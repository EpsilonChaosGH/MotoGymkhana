package com.example.motogymkhana.data


import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavoritesFlow(): Flow<List<Long>>

    suspend fun addStageIdToFavorites(id: Long)

    suspend fun deleteFromFavoritesByStageId(id: Long)
}