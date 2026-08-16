package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ItemPriority(val label: String, val level: Int) {
    LOW("Low", 1),
    MEDIUM("Medium", 2),
    HIGH("High", 3)
}

enum class ItemCategory(val label: String, val iconName: String) {
    FEATURE("Feature", "Star"),
    BUG("Bug Fix", "Build"),
    DESIGN("UI/UX", "Palette"),
    RESEARCH("Research", "Search"),
    GENERAL("General", "Folder")
}

@Entity(tableName = "app_items")
data class AppItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val category: ItemCategory = ItemCategory.FEATURE,
    val priority: ItemPriority = ItemPriority.MEDIUM,
    val isCompleted: Boolean = false,
    val isFavorite: Boolean = false,
    val tags: String = "", // Comma separated tags
    val progress: Int = 0, // 0 to 100
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    val tagList: List<String>
        get() = if (tags.isBlank()) emptyList() else tags.split(",").map { it.trim() }.filter { it.isNotEmpty() }
}
