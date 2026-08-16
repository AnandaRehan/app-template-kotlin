package com.example.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.AppItemEntity
import com.example.data.local.entity.ItemCategory
import com.example.ui.components.ArchitectureGuideDialog
import com.example.ui.components.CategoryChip
import com.example.ui.components.EmptyStateView
import com.example.ui.components.ItemCard
import com.example.ui.components.SearchBarField
import com.example.ui.components.SectionHeader
import com.example.ui.theme.PolishBadgeLight
import com.example.ui.theme.PolishDeepPurple
import com.example.ui.theme.PolishHeroLavender

@Composable
fun DashboardScreen(
    uiState: DashboardUiState,
    onSearchChange: (String) -> Unit,
    onCategorySelect: (ItemCategory?) -> Unit,
    onToggleCompleted: (AppItemEntity) -> Unit,
    onToggleFavorite: (AppItemEntity) -> Unit,
    onDeleteItem: (AppItemEntity) -> Unit,
    onQuickAdd: (String) -> Unit,
    onNavigateToItems: () -> Unit,
    onNavigateToCatalog: () -> Unit,
    onEditItem: (AppItemEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var showArchitectureGuide by remember { mutableStateOf(false) }
    var quickInputText by remember { mutableStateOf("") }

    if (showArchitectureGuide) {
        ArchitectureGuideDialog(onDismiss = { showArchitectureGuide = false })
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(bottom = 100.dp, top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header Bar (Professional Polish M3 Style)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Apps,
                            contentDescription = "App Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Template Core",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                IconButton(
                    onClick = { showArchitectureGuide = true },
                    modifier = Modifier.testTag("btn_profile_guide")
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile and Guide",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        // Hero Banner Card (Lavender bg with deep purple text & capsule badges)
        item {
            ProfessionalHeroBanner(
                onShowGuide = { showArchitectureGuide = true },
                onExploreComponents = onNavigateToCatalog
            )
        }

        // Search Bar with Polish Styling
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                SearchBarField(
                    query = uiState.searchQuery,
                    onQueryChange = onSearchChange,
                    placeholder = "Search modules, tasks, or tags..."
                )
            }
        }

        // Polish 2x2 Analytics & Metrics Grid
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "System Overview",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Live Metrics",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PolishGridCard(
                        title = "Analytics",
                        value = "${uiState.totalItems} Active Modules",
                        subtitle = "${uiState.completedItems} Completed",
                        icon = Icons.Default.Analytics,
                        modifier = Modifier.weight(1f)
                    )

                    PolishGridCard(
                        title = "Database",
                        value = "SQLite Room",
                        subtitle = "Synced & Reactive",
                        icon = Icons.Default.Storage,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PolishGridCard(
                        title = "In Progress",
                        value = "${uiState.inProgressItems} Tasks",
                        subtitle = "Active Sprint",
                        icon = Icons.Default.PlayArrow,
                        modifier = Modifier.weight(1f)
                    )

                    PolishGridCard(
                        title = "Core Features",
                        value = "${uiState.favoriteItems} Starred",
                        subtitle = "High Priority",
                        icon = Icons.Default.Favorite,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Quick Add Item Input
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = quickInputText,
                        onValueChange = { quickInputText = it },
                        placeholder = { Text("Quick add new task or module...") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("quick_add_input"),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (quickInputText.isNotBlank()) {
                                onQuickAdd(quickInputText.trim())
                                quickInputText = ""
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.testTag("quick_add_button")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        }

        // Category Filter Chips
        item {
            Column {
                SectionHeader(
                    title = "Filter by Module Category",
                    subtitle = "Instant reactive filtering",
                    actionLabel = if (uiState.activeCategoryFilter != null) "Clear" else null,
                    onActionClick = { onCategorySelect(null) }
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onCategorySelect(null) }
                                .testTag("category_all"),
                            shape = RoundedCornerShape(12.dp),
                            color = if (uiState.activeCategoryFilter == null) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surfaceVariant
                            }
                        ) {
                            Text(
                                text = "All (${uiState.totalItems})",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (uiState.activeCategoryFilter == null) FontWeight.Bold else FontWeight.Medium,
                                color = if (uiState.activeCategoryFilter == null) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }

                    items(ItemCategory.values()) { category ->
                        CategoryChip(
                            category = category,
                            isSelected = uiState.activeCategoryFilter == category,
                            onClick = { onCategorySelect(category) }
                        )
                    }
                }
            }
        }

        // Recent Items Section with Polish Container Header
        item {
            SectionHeader(
                title = "Recent Modules & Data",
                subtitle = "Synced with local Room SQLite",
                actionLabel = "See All",
                onActionClick = onNavigateToItems
            )
        }

        if (uiState.recentItems.isEmpty()) {
            item {
                EmptyStateView(
                    title = "No Items Found",
                    description = if (uiState.searchQuery.isNotEmpty()) {
                        "No matches for '${uiState.searchQuery}'. Try another query."
                    } else {
                        "Your database is ready. Add a new project module or task above!"
                    },
                    icon = Icons.Outlined.Search
                )
            }
        } else {
            items(uiState.recentItems, key = { it.id }) { item ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    ItemCard(
                        item = item,
                        onClick = { onEditItem(item) },
                        onToggleCompleted = { onToggleCompleted(item) },
                        onToggleFavorite = { onToggleFavorite(item) },
                        onEdit = { onEditItem(item) },
                        onDelete = { onDeleteItem(item) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfessionalHeroBanner(
    onShowGuide: () -> Unit,
    onExploreComponents: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("hero_banner_card"),
        shape = RoundedCornerShape(28.dp),
        color = PolishHeroLavender,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Project Overview",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PolishDeepPurple
            )

            Text(
                text = "Build your next big idea using this production-ready Material 3 foundation with Room SQLite and Clean Architecture.",
                style = MaterialTheme.typography.bodyMedium,
                color = PolishDeepPurple.copy(alpha = 0.9f),
                lineHeight = 20.sp
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                // Active Badge Pill
                Surface(
                    shape = CircleShape,
                    color = PolishDeepPurple
                ) {
                    Text(
                        text = "Active",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

                // Version Badge Pill
                Surface(
                    shape = CircleShape,
                    color = PolishBadgeLight
                ) {
                    Text(
                        text = "v1.0.4",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF21005D),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onShowGuide,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PolishDeepPurple,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.testTag("hero_guide_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Architecture", fontWeight = FontWeight.Bold)
                }

                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .clickable(onClick = onExploreComponents)
                        .testTag("hero_ui_kit_button"),
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = PolishDeepPurple,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "UI Catalog",
                            color = PolishDeepPurple,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PolishGridCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        shadowElevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFEADDFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFF21005D),
                    modifier = Modifier.size(20.dp)
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
