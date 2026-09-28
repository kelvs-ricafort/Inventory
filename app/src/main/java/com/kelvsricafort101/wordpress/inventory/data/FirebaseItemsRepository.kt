package com.kelvsricafort101.wordpress.inventory.data

import android.content.Context
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.memoryCacheSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class FirebaseItemsRepository(
    context: Context,
    private val localRepository: OfflineItemsRepository
): ItemsRepository {
    private val applicationContext = context.applicationContext
    private val networkMonitor = NetworkMonitor(applicationContext)
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance().apply {
        firestoreSettings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {})
        }
    }

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )

    private val syncMutex = Mutex()

    private val networkCallback = networkMonitor.registerOnAvailableListener {
        scope.launch {
            sync()
        }
    }

    init {
        if (networkMonitor.isOnline()) {
            scope.launch {
                sync()
            }
        }
    }

    override fun getAllItemsStream(): Flow<List<Item>> {
        return localRepository.getAllItemsStream()
    }

    override fun getItemStream(id: Int): Flow<Item?> {
        return localRepository.getItemStream(id)
    }

    override suspend fun insertItem(item: Item) {
        localRepository.insertItem(item)

        if (networkMonitor.isOnline()) {
            syncItem(item)
        }
    }

    override suspend fun updateItem(item: Item) {
        localRepository.updateItem(item)

        if (networkMonitor.isOnline()) {
            syncItem(item)
        }
    }

    override suspend fun deleteItem(item: Item) {
        localRepository.deleteItem(item)

        if (networkMonitor.isOnline()) {
            deleteFirebaseItem(item.id)
        }
    }

    suspend fun sync() {
        if (!networkMonitor.isOnline()) {
            return
        }
        syncMutex.withLock {
            try {
                authenticate()

                val localItems = localRepository
                    .getAllItemsStream()
                    .first()

                val remoteSnapshot = withContext(Dispatchers.IO) {
                    Tasks.await(
                        firestore
                            .collection(ITEMS_COLLECTION)
                            .get(Source.SERVER)
                    )
                }

                /**
                 * If Room is empty, download the existing Firebase inventory first.
                 */
                if (localItems.isEmpty() && remoteSnapshot.documents.isNotEmpty()) {
                    remoteSnapshot.documents.forEach { document ->
                        val item = document.toItem()

                        localRepository.insertItem(item)
                    }
                    return
                }

                /**
                 * Room is the source of truth.
                 * Upload all local items and remove Firebase items
                 * that no longer exist locally.
                 */
                val localIds = localItems
                    .map { it.id.toString() }
                    .toSet()

                remoteSnapshot.documents
                    .filter { it.id !in localIds }
                    .forEach { document ->
                        Tasks.await(
                            document.reference.delete()
                        )
                    }

                localItems.forEach { item ->
                    syncItem(item)
                }
            } catch (_: Exception) {
                // The item has already been deleted locally.
                // The next full sync will reconcile Firebase.
            }
        }
    }

    private suspend fun syncItem(item: Item) {
        if (!networkMonitor.isOnline()) {
            return
        }

        try {
            authenticate()

            withContext(Dispatchers.IO) {
                Tasks.await(
                    firestore
                        .collection(ITEMS_COLLECTION)
                        .document(item.id.toString())
                        .set(item.toFirebaseMap())
                )
            }
        } catch (_: Exception) {
            // Room remains the source of truth
        }
    }

    private suspend fun deleteFirebaseItem(id: Int) {
        if (!networkMonitor.isOnline()) {
            return
        }

        try {
            authenticate()

            withContext(Dispatchers.IO) {
                Tasks.await(
                    firestore
                        .collection(ITEMS_COLLECTION)
                        .document(id.toString())
                        .delete()
                )
            }
        } catch (_: Exception) {
            // The item has already been deleted locally.
            // The next full sync will reconcile Firebase.
        }
    }

    private suspend fun authenticate() {
        if (auth.currentUser != null) {
            return
        }

        withContext(Dispatchers.IO) {
            Tasks.await(
                auth.signInAnonymously()
            )
        }
    }

    private fun Item.toFirebaseMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "name" to name,
            "price" to price,
            "quantity" to quantity
        )
    }

    private fun DocumentSnapshot.toItem(): Item {
        return Item(
            id = getLong("id")?.toInt() ?: id.toInt(),
            name = getString("name") ?: "",
            price = getDouble("price") ?: 0.0,
            quantity = getLong("quantity")?.toInt() ?: 0)
    }

    companion object {
        private const val ITEMS_COLLECTION = "items"
    }
}