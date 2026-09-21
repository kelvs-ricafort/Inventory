package com.kelvsricafort101.wordpress.inventory.ui.home

import androidx.lifecycle.ViewModel
import com.kelvsricafort101.wordpress.inventory.data.Item

class HomeViewModel: ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Ui State for Home Screen
 */
data class HomeUiState(val itemList: List<Item> = listOf())