package com.kelvsricafort101.wordpress.inventory

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kelvsricafort101.wordpress.inventory.ui.navigation.InventoryNavHost
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsDataStore
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsViewModel
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsViewModelFactory
import com.kelvsricafort101.wordpress.inventory.ui.theme.InventoryTheme
import java.util.Locale

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun InventoryApp(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val currentConfiguration = LocalConfiguration.current
    val settingsDataStore = remember { SettingsDataStore(context = context.applicationContext) }
    val settingsViewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(settingsDataStore = settingsDataStore))
    val settingsUiState by settingsViewModel.uiState.collectAsState()

    val configuration = remember(settingsUiState.appLanguage.code, context) {
        Configuration(currentConfiguration).apply {
            setLocale(Locale.forLanguageTag(settingsUiState.appLanguage.code))
        }
    }

    val localizedContext = remember(settingsUiState.appLanguage.code, context) {
        context.createConfigurationContext(configuration)
    }

    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides configuration
    ) {

        InventoryTheme(
            darkTheme = settingsUiState.darkMode
        ) {
            InventoryNavHost(
                navController = navController,
                settingsUiState = settingsUiState,
                onDarkModeChanged = settingsViewModel::setDarkMode,
                onLanguageSelected = settingsViewModel::setLanguage
            )
        }
    }
}

/**
 * App bar to display title and conditionally display the back navigation.
 */
@Composable
fun InventoryTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            } else {
                navigationIcon()
            }
        },
        actions = actions
    )
}