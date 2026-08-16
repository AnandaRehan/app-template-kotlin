package com.example.data.repository

import com.example.data.local.dao.AppItemDao
import com.example.data.local.entity.AppItemEntity
import kotlinx.coroutines.flow.Flow

interface AppItemRepository {
    fun getAllItems(): Flow<List<AppItemEntity>>
    fun getItemById(id: Long): Flow<AppItemEntity?>
    suspend fun getItemByIdDirect(id: Long): AppItemEntity?
    fun searchItems(query: String): Flow<List<AppItemEntity>>
    fun getItemCount(): Flow<Int>
    fun getCompletedCount(): Flow<Int>
    fun getFavoriteCount(): Flow<Int>
    suspend fun insertItem(item: AppItemEntity): Long
    suspend fun insertItems(items: List<AppItemEntity>): List<Long>
    suspend fun updateItem(item: AppItemEntity)
    suspend fun toggleCompleted(item: AppItemEntity)
    suspend fun toggleFavorite(item: AppItemEntity)
    suspend fun deleteItem(item: AppItemEntity)
    suspend fun deleteItemById(id: Long)
    suspend fun deleteAll()
}

class AppItemRepositoryImpl(
    private val appItemDao: AppItemDao
) : AppItemRepository {
    override fun getAllItems(): Flow<List<AppItemEntity>> = appItemDao.getAllItems()

    override fun getItemById(id: Long): Flow<AppItemEntity?> = appItemDao.getItemById(id)

    override suspend fun getItemByIdDirect(id: Long): AppItemEntity? = appItemDao.getItemByIdDirect(id)

    override fun searchItems(query: String): Flow<List<AppItemEntity>> = appItemDao.searchItems(query)

    override fun getItemCount(): Flow<Int> = appItemDao.getItemCount()

    override fun getCompletedCount(): Flow<Int> = appItemDao.getCompletedCount()

    override fun getFavoriteCount(): Flow<Int> = appItemDao.getFavoriteCount()

    override suspend fun insertItem(item: AppItemEntity): Long = appItemDao.insertItem(item)

    override suspend fun insertItems(items: List<AppItemEntity>): List<Long> = appItemDao.insertItems(items)

    override suspend fun updateItem(item: AppItemEntity) = appItemDao.updateItem(
        item.copy(updatedAt = System.currentTimeMillis())
    )

    override suspend fun toggleCompleted(item: AppItemEntity) {
        val newStatus = !item.isCompleted
        val newProgress = if (newStatus) 100 else 0
        appItemDao.updateItem(
            item.copy(
                isCompleted = newStatus,
                progress = newProgress,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun toggleFavorite(item: AppItemEntity) {
        appItemDao.updateItem(
            item.copy(
                isFavorite = !item.isFavorite,
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun deleteItem(item: AppItemEntity) = appItemDao.deleteItem(item)

    override suspend fun deleteItemById(id: Long) = appItemDao.deleteItemById(id)

    override suspend fun deleteAll() = appItemDao.deleteAll()
}
