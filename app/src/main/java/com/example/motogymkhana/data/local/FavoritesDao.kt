package com.example.motogymkhana.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun observeFavorites(): Flow<List<FavoritesDbEntity>>

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<FavoritesDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoritesDbEntity: FavoritesDbEntity)

    @Query("DELETE FROM favorites WHERE stage_id = :stageId")
    suspend fun deleteFromFavorites(stageId: Long)

    @Query("SELECT EXISTS(SELECT stage_id FROM favorites WHERE stage_id =:id)")
    fun checkForFavorites(id: Long): Boolean
}