package com.kelvsricafort101.wordpress.inventory.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelvsricafort101.wordpress.inventory.R
import com.kelvsricafort101.wordpress.inventory.data.AppLanguage
import com.kelvsricafort101.wordpress.inventory.ui.theme.InventoryTheme

@Composable
fun LanguageSelector(
    appLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = appLanguage.displayName,
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = stringResource(R.string.language)
                )
            },
            leadingIcon = {
                Image(
                    painter = painterResource(appLanguage.flagRes),
                    contentDescription = appLanguage.displayName,
                    modifier = Modifier.size(width = 24.dp, height = 18.dp)
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor(
                type = ExposedDropdownMenuAnchorType.PrimaryNotEditable
            ).fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            AppLanguage.entries.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(language.flagRes),
                                contentDescription = language.displayName,
                                modifier = Modifier.size(width = 24.dp, height = 18.dp)
                            )
                            Text(
                                text = language.displayName
                            )
                        }
                    },
                    onClick = {
                        onLanguageSelected(language)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectorPreview() {
    InventoryTheme {
        LanguageSelector(
            appLanguage = AppLanguage.ENGLISH,
            onLanguageSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectorFilipinoPreview() {
    InventoryTheme {
        LanguageSelector(
            appLanguage = AppLanguage.FILIPINO,
            onLanguageSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectorSpanishPreview() {
    InventoryTheme {
        LanguageSelector(
            appLanguage = AppLanguage.SPANISH,
            onLanguageSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectorPreviewDark() {
    InventoryTheme(darkTheme = true) {
        Surface {
            LanguageSelector(
                appLanguage = AppLanguage.ENGLISH,
                onLanguageSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectorFilipinoDarkPreview() {
    InventoryTheme(darkTheme = true) {
        Surface {
            LanguageSelector(
                appLanguage = AppLanguage.FILIPINO,
                onLanguageSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectorSpanishDarkPreview() {
    InventoryTheme(darkTheme = true) {
        Surface {
            LanguageSelector(
                appLanguage = AppLanguage.SPANISH,
                onLanguageSelected = {}
            )
        }
    }
}