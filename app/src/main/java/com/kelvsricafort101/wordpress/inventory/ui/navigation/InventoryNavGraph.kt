package com.kelvsricafort101.wordpress.inventory.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.kelvsricafort101.wordpress.inventory.data.AppLanguage
import com.kelvsricafort101.wordpress.inventory.drawer.InventoryNavigationDrawer
import com.kelvsricafort101.wordpress.inventory.ui.aboutapp.AboutAppDestination
import com.kelvsricafort101.wordpress.inventory.ui.aboutapp.AboutAppScreen
import com.kelvsricafort101.wordpress.inventory.ui.home.HomeDestination
import com.kelvsricafort101.wordpress.inventory.ui.home.HomeScreen
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemDetailsDestination
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemDetailsScreen
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemEditDestination
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemEditScreen
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemEntryDestination
import com.kelvsricafort101.wordpress.inventory.ui.item.ItemEntryScreen
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsDestination
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsScreen
import com.kelvsricafort101.wordpress.inventory.ui.settings.SettingsUiState

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    settingsUiState: SettingsUiState,
    onDarkModeChanged: (Boolean) -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    InventoryNavigationDrawer(
        selectedRoute = currentRoute,
        onNavigate = { route ->
            navController.navigate(route) {
                popUpTo(HomeDestination.route) {
                    saveState = true
                }

                launchSingleTop = true
                restoreState = true
            }
        }
    ) { openDrawer ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route,
            modifier = modifier
        ) {
            composable(route = HomeDestination.route) {
                HomeScreen(
                    navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                    navigateToItemUpdate = {
                        navController.navigate("${ItemDetailsDestination.route}/${it}")
                    },
                    navigateToAboutApp = {
                        navController.navigate(AboutAppDestination.route)
                    },
                    openDrawer = openDrawer
                )
            }
            // AboutApp
            composable(route = AboutAppDestination.route) {
                AboutAppScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    }
                )
            }
            // SettingsScreen
            composable(route = SettingsDestination.route) {
                SettingsScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    darkMode = settingsUiState.darkMode,
                    onDarkModeChange = onDarkModeChanged,
                    selectedLanguage = settingsUiState.appLanguage,
                    onLanguageSelected = onLanguageSelected
                )
            }
            // ItemEntryScreen
            composable(route = ItemEntryDestination.route) {
                ItemEntryScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                )
            }
            composable(
                route = ItemDetailsDestination.routeWithArgs,
                arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                    type = NavType.IntType
                })
            ) {
                ItemDetailsScreen(
                    navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                    navigateBack = { navController.navigateUp() }
                )
            }
            composable(
                route = ItemEditDestination.routeWithArgs,
                arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                    type = NavType.IntType
                })
            ) {
                ItemEditScreen(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}