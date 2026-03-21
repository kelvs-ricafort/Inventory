package com.kelvsricafort101.wordpress.inventory.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kelvsricafort101.wordpress.inventory.InventoryApplication
import com.kelvsricafort101.wordpress.inventory.ui.home.HomeViewModel
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemDetailsViewModel
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemEditViewModel
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory App
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle()
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            ItemEntryViewModel()
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle()
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[APPLICATION_KEY] as InventoryApplication)