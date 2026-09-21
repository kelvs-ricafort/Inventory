package com.kelvsricafort101.wordpress.inventory

import android.app.Application
import com.kelvsricafort101.wordpress.inventory.data.AppContainer
import com.kelvsricafort101.wordpress.inventory.data.AppDataContainer

class InventoryApplication: Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}