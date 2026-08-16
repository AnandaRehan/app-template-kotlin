package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entity.AppItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppItemDao {
    @Query("SELECT * FROM app_items ORDER BY isFavorite DESC, updatedAt DESC")
    fun getAllItems(): Flow<List<AppItemEntity>>

    @Query("SELECT * FROM app_items WHERE id = :id LIMIT 1")
    fun getItemById(id: Long): Flow<AppItemEntity?>

    @Query("SELECT * FROM app_items WHERE id = :id LIMIT 1")
    suspend fun getItemByIdDirect(id: Long): AppItemEntity?

    @Query("SELECT * FROM app_items WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%' ORDER BY updatedAt DESC")
    fun searchItems(query: String): Flow<List<AppItemEntity>>

    @Query("SELECT COUNT(*) FROM app_items")
    fun getItemCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM app_items WHERE isCompleted = 1")
    fun getCompletedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM app_items WHERE isFavorite = 1")
    fun getFavoriteCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: AppItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<AppItemEntity>): List<Long>

    @Update
    suspend fun updateItem(item: AppItemEntity)

    @Delete
    suspend fun deleteItem(item: AppItemEntity)

    @Query("DELETE FROM app_items WHERE id = :id")
    suspend fun deleteItemById(id: Long)

    @Query("DELETE FROM app_items")
    suspend fun deleteAll()
}
