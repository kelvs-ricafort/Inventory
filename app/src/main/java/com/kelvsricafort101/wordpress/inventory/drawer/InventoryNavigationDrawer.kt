package com.kelvsricafort101.wordpress.inventory.drawer

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kelvsricafort101.wordpress.inventory.R
import com.kelvsricafort101.wordpress.inventory.ui.aboutapp.AboutAppDestination
import com.kelvsricafort101.wordpress.inventory.ui.home.HomeDestination
import kotlinx.coroutines.launch

@Composable
fun InventoryNavigationDrawer(
    selectedRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (openDrawer: () -> Unit) -> Unit
) {
    val drawerState = rememberDrawerState(
       initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(24.dp)
                )
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(R.string.app_name)
                        )
                    },
                    selected = selectedRoute == HomeDestination.route,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onNavigate(HomeDestination.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Inventory,
                            contentDescription = null
                        )
                    }
                )

                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(R.string.about_app)
                        )
                    },
                    selected = selectedRoute == AboutAppDestination.route,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onNavigate(AboutAppDestination.route)
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    ) {
        content {
            scope.launch {
                drawerState.open()
            }
        }
    }
}