package com.example.ui.screens.items

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.local.entity.AppItemEntity
import com.example.data.local.entity.ItemCategory
import com.example.data.local.entity.ItemPriority
import com.example.ui.components.CategoryChip
import com.example.ui.components.ConfirmationDialog
import com.example.ui.components.EmptyStateView
import com.example.ui.components.ItemCard
import com.example.ui.components.SearchBarField

@Composable
fun ItemListScreen(
    uiState: ItemsUiState,
    onSearchQueryChange: (String) -> Unit,
    onCategorySelect: (ItemCategory?) -> Unit,
    onPrioritySelect: (ItemPriority?) -> Unit,
    onToggleFavoritesFilter: () -> Unit,
    onToggleCompletedFilter: () -> Unit,
    onSetSortOption: (ItemSortOption) -> Unit,
    onOpenAddItem: () -> Unit,
    onOpenEditItem: (AppItemEntity) -> Unit,
    onCloseAddEdit: () -> Unit,
    onSaveItem: (
        title: String,
        description: String,
        category: ItemCategory,
        priority: ItemPriority,
        tags: String,
        progress: Int
    ) -> Unit,
    onToggleCompleted: (AppItemEntity) -> Unit,
    onToggleFavorite: (AppItemEntity) -> Unit,
    onDeleteItem: (AppItemEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var itemToDelete by remember { mutableStateOf<AppItemEntity?>(null) }
    var sortMenuOpen by remember { mutableStateOf(false) }

    if (uiState.isAddEditOpen) {
        AddEditItemDialog(
            item = uiState.selectedItemForEdit,
            onSave = onSaveItem,
            onDismiss = onCloseAddEdit
        )
    }

    if (itemToDelete != null) {
        ConfirmationDialog(
            title = "Delete Item",
            message = "Are you sure you want to delete '${itemToDelete?.title}' from the database?",
            confirmText = "Delete",
            isDestructive = true,
            onConfirm = {
                itemToDelete?.let { onDeleteItem(it) }
                itemToDelete = null
            },
            onDismiss = { itemToDelete = null }
        )
    }

    Scaffold(
        modifier = modifier.testTag("item_list_screen"),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onOpenAddItem,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .testTag("fab_add_item")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Item")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Item", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Screen Header & Search Bar
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Data & Project Items",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Local Room persistence with reactive Flow",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Box {
                            IconButton(
                                onClick = { sortMenuOpen = true },
                                modifier = Modifier.testTag("btn_sort_menu")
                            ) {
                                Icon(
                                    Icons.Default.Sort,
                                    contentDescription = "Sort",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            DropdownMenu(
                                expanded = sortMenuOpen,
                                onDismissRequest = { sortMenuOpen = false }
                            ) {
                                ItemSortOption.values().forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(option.label)
                                                if (uiState.sortOption == option) {
                                                    Icon(
                                                        Icons.Default.Check,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.primary,
                                                        modifier = Modifier.size(16.dp)
                                                    )
                                                }
                                            }
                                        },
                                        onClick = {
                                            onSetSortOption(option)
                                            sortMenuOpen = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    SearchBarField(
                        query = uiState.searchQuery,
                        onQueryChange = onSearchQueryChange,
                        placeholder = "Search by title, description, or tag..."
                    )
                }
            }

            // Filter Chips Row
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        FilterChip(
                            selected = uiState.filterFavoritesOnly,
                            onClick = onToggleFavoritesFilter,
                            label = { Text("Starred") },
                            leadingIcon = {
                                Icon(
                                    imageVector = if (uiState.filterFavoritesOnly) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("filter_fav_chip")
                        )
                    }

                    item {
                        FilterChip(
                            selected = uiState.filterCompletedOnly,
                            onClick = onToggleCompletedFilter,
                            label = { Text("Completed") },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("filter_completed_chip")
                        )
                    }

                    items(ItemCategory.values()) { category ->
                        CategoryChip(
                            category = category,
                            isSelected = uiState.selectedCategory == category,
                            onClick = { onCategorySelect(category) }
                        )
                    }
                }
            }

            // Results count banner
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Showing ${uiState.items.size} items",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Sort: ${uiState.sortOption.label}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Items List
            if (uiState.items.isEmpty()) {
                item {
                    EmptyStateView(
                        title = "No Items Match Criteria",
                        description = "Try clearing filters or click '+ Add Item' to insert new records into the SQLite database.",
                        icon = Icons.Outlined.Inbox,
                        actionButton = {
                            OutlinedButton(
                                onClick = onOpenAddItem,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("empty_state_add_button")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Add First Item")
                            }
                        }
                    )
                }
            } else {
                items(uiState.items, key = { it.id }) { item ->
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ItemCard(
                            item = item,
                            onClick = { onOpenEditItem(item) },
                            onToggleCompleted = { onToggleCompleted(item) },
                            onToggleFavorite = { onToggleFavorite(item) },
                            onEdit = { onOpenEditItem(item) },
                            onDelete = { itemToDelete = item }
                        )
                    }
                }
            }
        }
    }
}
