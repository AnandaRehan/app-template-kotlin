package com.example.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.TemplateApplication
import com.example.ui.screens.catalog.ComponentCatalogScreen
import com.example.ui.screens.dashboard.DashboardScreen
import com.example.ui.screens.dashboard.DashboardViewModel
import com.example.ui.screens.items.ItemListScreen
import com.example.ui.screens.items.ItemsViewModel
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.settings.SettingsViewModel

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    app: TemplateApplication = TemplateApplication.instance,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Dashboard.route

    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModel.provideFactory(app.itemRepository)
    )
    val itemsViewModel: ItemsViewModel = viewModel(
        factory = ItemsViewModel.provideFactory(app.itemRepository)
    )
    val settingsViewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModel.provideFactory(app.preferencesRepository, app.itemRepository)
    )

    val dashboardUiState by dashboardViewModel.uiState.collectAsStateWithLifecycle()
    val itemsUiState by itemsViewModel.uiState.collectAsStateWithLifecycle()
    val settingsUiState by settingsViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            androidx.compose.material3.Surface(
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                NavigationBar(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .testTag("bottom_nav_bar"),
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp
                ) {
                    Screen.bottomNavItems.forEach { screen ->
                        val selected = currentRoute == screen.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.title
                                )
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.testTag("nav_item_${screen.route}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = Screen.Dashboard.route,
                enterTransition = { fadeIn(animationSpec = tween(220)) },
                exitTransition = { fadeOut(animationSpec = tween(220)) }
            ) {
                DashboardScreen(
                    uiState = dashboardUiState,
                    onSearchChange = dashboardViewModel::onSearchQueryChange,
                    onCategorySelect = dashboardViewModel::selectCategory,
                    onToggleCompleted = dashboardViewModel::toggleCompleted,
                    onToggleFavorite = dashboardViewModel::toggleFavorite,
                    onDeleteItem = dashboardViewModel::deleteItem,
                    onQuickAdd = dashboardViewModel::quickAddItem,
                    onNavigateToItems = {
                        navController.navigate(Screen.Items.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onNavigateToCatalog = {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onEditItem = { item ->
                        itemsViewModel.openEditItem(item)
                        navController.navigate(Screen.Items.route)
                    }
                )
            }

            composable(
                route = Screen.Items.route,
                enterTransition = { fadeIn(animationSpec = tween(220)) },
                exitTransition = { fadeOut(animationSpec = tween(220)) }
            ) {
                ItemListScreen(
                    uiState = itemsUiState,
                    onSearchQueryChange = itemsViewModel::onSearchQueryChange,
                    onCategorySelect = itemsViewModel::selectCategory,
                    onPrioritySelect = itemsViewModel::selectPriority,
                    onToggleFavoritesFilter = itemsViewModel::toggleFavoritesFilter,
                    onToggleCompletedFilter = itemsViewModel::toggleCompletedFilter,
                    onSetSortOption = itemsViewModel::setSortOption,
                    onOpenAddItem = itemsViewModel::openAddItem,
                    onOpenEditItem = itemsViewModel::openEditItem,
                    onCloseAddEdit = itemsViewModel::closeAddEdit,
                    onSaveItem = itemsViewModel::saveItem,
                    onToggleCompleted = itemsViewModel::toggleCompleted,
                    onToggleFavorite = itemsViewModel::toggleFavorite,
                    onDeleteItem = itemsViewModel::deleteItem
                )
            }

            composable(
                route = Screen.Catalog.route,
                enterTransition = { fadeIn(animationSpec = tween(220)) },
                exitTransition = { fadeOut(animationSpec = tween(220)) }
            ) {
                ComponentCatalogScreen()
            }

            composable(
                route = Screen.Settings.route,
                enterTransition = { fadeIn(animationSpec = tween(220)) },
                exitTransition = { fadeOut(animationSpec = tween(220)) }
            ) {
                SettingsScreen(
                    uiState = settingsUiState,
                    onSetThemeMode = settingsViewModel::setThemeMode,
                    onSetColorPalette = settingsViewModel::setColorPalette,
                    onToggleDynamicColor = settingsViewModel::toggleDynamicColor,
                    onToggleQuickTips = settingsViewModel::toggleQuickTips,
                    onToggleDeveloperMode = settingsViewModel::toggleDeveloperMode,
                    onResetDemoData = settingsViewModel::resetDemoData,
                    onClearAllData = settingsViewModel::clearAllData
                )
            }
        }
    }
}
