package com.example.ui.screens.catalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ConfirmationDialog
import com.example.ui.components.SectionHeader
import com.example.ui.theme.StatusError
import com.example.ui.theme.StatusInfo
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.StatusWarning
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ComponentCatalogScreen(
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var textInputSample by remember { mutableStateOf("") }
    var switchState by remember { mutableStateOf(true) }
    var checkboxState by remember { mutableStateOf(true) }
    var selectedRadio by remember { mutableIntStateOf(1) }
    var sliderValue by remember { mutableFloatStateOf(65f) }
    var showDemoDialog by remember { mutableStateOf(false) }
    var badgeCount by remember { mutableIntStateOf(5) }

    if (showDemoDialog) {
        ConfirmationDialog(
            title = "Material 3 Dialog Sample",
            message = "This is a reusable confirmation dialog from com.example.ui.components. Perfect for user actions and alerts.",
            confirmText = "Awesome",
            dismissText = "Dismiss",
            onConfirm = {
                showDemoDialog = false
                scope.launch { snackbarHostState.showSnackbar("Dialog confirmed!") }
            },
            onDismiss = { showDemoDialog = false }
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("catalog_screen"),
            contentPadding = PaddingValues(bottom = 120.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Screen Header
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = "UI Kit & Components",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Interactive Material 3 showcase ready to copy & use",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 1. Buttons Showcase
            item {
                CatalogCard(title = "1. Buttons & Actions") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    scope.launch { snackbarHostState.showSnackbar("Filled Button Clicked") }
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Filled")
                            }

                            FilledTonalButton(
                                onClick = {
                                    scope.launch { snackbarHostState.showSnackbar("Tonal Button Clicked") }
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Filled Tonal")
                            }

                            ElevatedButton(
                                onClick = {
                                    scope.launch { snackbarHostState.showSnackbar("Elevated Button Clicked") }
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Elevated")
                            }

                            OutlinedButton(
                                onClick = {
                                    scope.launch { snackbarHostState.showSnackbar("Outlined Button Clicked") }
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Outlined")
                            }

                            TextButton(
                                onClick = {
                                    scope.launch { snackbarHostState.showSnackbar("Text Button Clicked") }
                                }
                            ) {
                                Text("Text Button")
                            }
                        }
                    }
                }
            }

            // 2. Chips & Tags Showcase
            item {
                CatalogCard(title = "2. Chips & Badges") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AssistChip(
                                onClick = {},
                                label = { Text("Assist Chip") },
                                leadingIcon = { Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp)) }
                            )

                            FilterChip(
                                selected = true,
                                onClick = {},
                                label = { Text("Active Filter") },
                                leadingIcon = { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                            )

                            SuggestionChip(
                                onClick = {},
                                label = { Text("Suggestion") }
                            )

                            BadgedBox(
                                badge = {
                                    Badge(containerColor = MaterialTheme.colorScheme.error) {
                                        Text("$badgeCount")
                                    }
                                }
                            ) {
                                IconButton(onClick = { badgeCount++ }) {
                                    Icon(Icons.Default.Notifications, contentDescription = "Alerts")
                                }
                            }
                        }
                    }
                }
            }

            // 3. Selection & Controls
            item {
                CatalogCard(title = "3. Selection Controls & Inputs") {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        OutlinedTextField(
                            value = textInputSample,
                            onValueChange = { textInputSample = it },
                            label = { Text("Interactive Input Field") },
                            placeholder = { Text("Type something here...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Switch Toggle",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Switch(
                                checked = switchState,
                                onCheckedChange = { switchState = it }
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Checkbox Option",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Checkbox(
                                checked = checkboxState,
                                onCheckedChange = { checkboxState = it }
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Radio Group:",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            listOf(1 to "A", 2 to "B", 3 to "C").forEach { (id, label) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable { selectedRadio = id }
                                ) {
                                    RadioButton(
                                        selected = selectedRadio == id,
                                        onClick = { selectedRadio = id }
                                    )
                                    Text(label, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }
            }

            // 4. Sliders & Progress Indicators
            item {
                CatalogCard(title = "4. Sliders & Progress") {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Continuous Slider", style = MaterialTheme.typography.bodyMedium)
                            Text("${sliderValue.toInt()}%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }

                        Slider(
                            value = sliderValue,
                            onValueChange = { sliderValue = it },
                            valueRange = 0f..100f
                        )

                        Text("Linear Progress Indicator", style = MaterialTheme.typography.bodySmall)
                        LinearProgressIndicator(
                            progress = { sliderValue / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { sliderValue / 100f },
                                modifier = Modifier.size(36.dp),
                                strokeWidth = 4.dp
                            )
                            CircularProgressIndicator(
                                modifier = Modifier.size(36.dp),
                                strokeWidth = 4.dp
                            )
                            Text(
                                "Determinate & Indeterminate spinners",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // 5. Semantic Status Banners
            item {
                CatalogCard(title = "5. Status Indicators & Alerts") {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        StatusBanner("Success: Operation completed successfully.", StatusSuccess, Color(0xFFD1FAE5))
                        StatusBanner("Warning: System memory usage elevated.", StatusWarning, Color(0xFFFEF3C7))
                        StatusBanner("Error: Failed to fetch remote payload.", StatusError, Color(0xFFFEE2E2))
                        StatusBanner("Info: New template build available.", StatusInfo, Color(0xFFDBEAFE))
                    }
                }
            }

            // 6. Interactive Dialogs & Snackbars
            item {
                CatalogCard(title = "6. Modals & Notifications") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { showDemoDialog = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Open Dialog")
                        }

                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("This is an M3 Snackbar with Action!")
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Show Snackbar")
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp)
        )
    }
}

@Composable
private fun CatalogCard(
    title: String,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(14.dp))
            content()
        }
    }
}

@Composable
private fun StatusBanner(
    message: String,
    iconColor: Color,
    containerColor: Color
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(iconColor)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1E293B)
            )
        }
    }
}
