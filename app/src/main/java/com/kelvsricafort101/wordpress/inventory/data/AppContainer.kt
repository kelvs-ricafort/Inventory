package com.kelvsricafort101.wordpress.inventory.data

import android.content.Context

/**
 * App Container for Dependency Injection.
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository].
 */
class AppDataContainer(private val context: Context): AppContainer {
    /**
     * Implementation for [ItemsRepository].
     */
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}