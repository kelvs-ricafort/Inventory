package com.kelvsricafort101.wordpress.inventory.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelvsricafort101.wordpress.inventory.InventoryTopAppBar
import com.kelvsricafort101.wordpress.inventory.R
import com.kelvsricafort101.wordpress.inventory.data.AppLanguage
import com.kelvsricafort101.wordpress.inventory.ui.navigation.NavigationDestination
import com.kelvsricafort101.wordpress.inventory.ui.theme.InventoryTheme

object SettingsDestination: NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings
}

@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            InventoryTopAppBar(
                title = stringResource(SettingsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = stringResource(R.string.appearance),
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.dark_mode),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = stringResource(R.string.dark_mode_description),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Switch(
                    checked = darkMode,
                    onCheckedChange = onDarkModeChanged
                )
            }
            HorizontalDivider()

            Text(
                text = stringResource(R.string.language),
                style = MaterialTheme.typography.titleLarge
            )

            LanguageSelector(
                selectedLanguage = selectedLanguage,
                onLanguageSelected = onLanguageSelected
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    InventoryTheme() {
        SettingsScreen(
            onNavigateUp = {},
            darkMode = false,
            onDarkModeChanged = {},
            selectedLanguage = AppLanguage.ENGLISH,
            onLanguageSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenDarkPreview() {
    InventoryTheme(darkTheme = true) {
        Surface {
            SettingsScreen(
                onNavigateUp = {},
                darkMode = false,
                onDarkModeChanged = {},
                selectedLanguage = AppLanguage.ENGLISH,
                onLanguageSelected = {}
            )
        }
    }
}