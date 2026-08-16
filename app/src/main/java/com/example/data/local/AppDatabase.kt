package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.AppItemDao
import com.example.data.local.entity.AppItemEntity
import com.example.data.local.entity.ItemCategory
import com.example.data.local.entity.ItemPriority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [AppItemEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appItemDao(): AppItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_template_database.db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(DatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }

        val initialDemoItems = listOf(
            AppItemEntity(
                id = 1,
                title = "Setup Project Architecture",
                description = "Configure Clean Architecture with MVVM, Room Database, and Navigation Compose.",
                category = ItemCategory.FEATURE,
                priority = ItemPriority.HIGH,
                isCompleted = true,
                isFavorite = true,
                tags = "Architecture, Kotlin, MVVM",
                progress = 100
            ),
            AppItemEntity(
                id = 2,
                title = "Material 3 Design System",
                description = "Build reusable UI components, dark mode toggle, and dynamic color theme switcher.",
                category = ItemCategory.DESIGN,
                priority = ItemPriority.HIGH,
                isCompleted = true,
                isFavorite = true,
                tags = "M3, Compose, UI",
                progress = 100
            ),
            AppItemEntity(
                id = 3,
                title = "Room Local Persistence",
                description = "Implement Flow-based DAO queries, repository pattern, and reactive UI state updates.",
                category = ItemCategory.FEATURE,
                priority = ItemPriority.MEDIUM,
                isCompleted = false,
                isFavorite = false,
                tags = "Room, SQLite, Cache",
                progress = 75
            ),
            AppItemEntity(
                id = 4,
                title = "Component Catalog & Showcase",
                description = "Interactive playground showcasing buttons, cards, modals, form inputs, and animations.",
                category = ItemCategory.DESIGN,
                priority = ItemPriority.MEDIUM,
                isCompleted = false,
                isFavorite = true,
                tags = "Showcase, Components",
                progress = 50
            ),
            AppItemEntity(
                id = 5,
                title = "API & Network Integration",
                description = "Add Retrofit/Ktor networking layer for connecting to REST APIs when needed.",
                category = ItemCategory.RESEARCH,
                priority = ItemPriority.LOW,
                isCompleted = false,
                isFavorite = false,
                tags = "Retrofit, Network, API",
                progress = 20
            )
        )
    }

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    database.appItemDao().insertItems(initialDemoItems)
                }
            }
        }
    }
}
