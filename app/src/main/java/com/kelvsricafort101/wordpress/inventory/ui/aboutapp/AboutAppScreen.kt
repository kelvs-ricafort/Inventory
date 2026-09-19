package com.kelvsricafort101.wordpress.inventory.ui.aboutapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kelvsricafort101.wordpress.inventory.BuildConfig
import com.kelvsricafort101.wordpress.inventory.R
import com.kelvsricafort101.wordpress.inventory.ui.navigation.NavigationDestination
import com.kelvsricafort101.wordpress.inventory.ui.theme.InventoryTheme

object AboutAppDestination: NavigationDestination {
    override val route = "About"
    override val titleRes = R.string.about_app
}

@Composable
fun AboutAppScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // App Icon
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = stringResource(R.string.about_app),
                    modifier = Modifier.fillMaxSize()
                )
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(R.string.about_app),
                    modifier = Modifier.fillMaxSize()
                )
            }
            // App Name
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            // App Description
            Text(
                text = stringResource(R.string.about_app_description),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            HorizontalDivider()

            // Version and Build
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val versionPrefix = stringResource(R.string.version, "").trim()
                val buildText = stringResource(R.string.build, BuildConfig.VERSION_CODE).trim()
                val buildPrefix = buildText.substringBefore(BuildConfig.VERSION_CODE.toString()).trim()

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("$versionPrefix" )
                        }
                        append(BuildConfig.VERSION_NAME)
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("$buildPrefix" )
                        }
                        append(BuildConfig.VERSION_CODE.toString())
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

                val devPrefix = stringResource(R.string.developer, "").trim()
                val devName = stringResource(R.string.developer_name)

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("$devPrefix ")
                        }
                        append(devName)
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            HorizontalDivider()

            // Features Section
            AboutSection(
                title = stringResource(R.string.features)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureItem(
                        icon = Icons.Default.Add,
                        text = stringResource(R.string.feature_add_item)
                    )
                    FeatureItem(
                        icon = Icons.Default.ShoppingCart,
                        text = stringResource(R.string.feature_sell_item)
                    )

                    FeatureItem(
                        icon = Icons.Default.Delete,
                        text = stringResource(R.string.feature_delete_item)
                    )

                    FeatureItem(
                        icon = Icons.Default.Inventory2,
                        text = stringResource(R.string.feature_track_quantity)
                    )

                    FeatureItem(
                        icon = Icons.Default.CloudOff,
                        text = stringResource(R.string.feature_offline)
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureItem(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun AboutSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        content()
    }
}

@Preview(showBackground = true)
@Composable
fun AboutAppScreenPreview() {
    InventoryTheme() {
        AboutAppScreen(
            onNavigateUp = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutAppScreenDarkPreview() {
    InventoryTheme(darkTheme = true) {
        AboutAppScreen(
            onNavigateUp = {}
        )
    }
}