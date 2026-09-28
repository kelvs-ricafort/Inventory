package com.kelvsricafort101.wordpress.inventory.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
    val firebaseItemsRepository: FirebaseItemsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    private val offlineItemsRepository: OfflineItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }

    override val firebaseItemsRepository: FirebaseItemsRepository by lazy {
        FirebaseItemsRepository(
            context = context,
            localRepository = offlineItemsRepository
        )
    }

    override val itemsRepository: ItemsRepository
        get() = firebaseItemsRepository
}