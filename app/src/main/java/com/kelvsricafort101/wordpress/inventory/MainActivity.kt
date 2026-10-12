package com.kelvsricafort101.wordpress.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsDataStore
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsViewModel
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsViewModelFactory
import com.kelvsricafort101.wordpress.inventory.ui.theme.InventoryTheme

class MainActivity : ComponentActivity() {
    private val settingsDataStore by lazy {
        SettingsDataStore(applicationContext)
    }

    private val settingsViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(settingsDataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsUiState by settingsViewModel.uiState.collectAsState()
            InventoryTheme(darkTheme = settingsUiState.darkMode) {
                // A surface container using the 'background' color from the theme.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InventoryApp()
                }
            }
        }
    }
}
