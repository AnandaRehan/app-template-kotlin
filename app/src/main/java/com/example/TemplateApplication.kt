package com.example

import android.app.Application
import com.example.data.local.AppDatabase
import com.example.data.repository.AppItemRepository
import com.example.data.repository.AppItemRepositoryImpl
import com.example.data.repository.AppPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class TemplateApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val itemRepository: AppItemRepository by lazy { AppItemRepositoryImpl(database.appItemDao()) }
    val preferencesRepository: AppPreferencesRepository by lazy { AppPreferencesRepository() }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: TemplateApplication
            private set
    }
}
