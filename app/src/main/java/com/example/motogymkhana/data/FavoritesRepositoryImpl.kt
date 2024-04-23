package com.example.motogymkhana.data

import com.example.motogymkhana.data.local.AppDatabase
import com.example.motogymkhana.data.local.FavoritesDbEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
) : FavoritesRepository {
    override fun getFavoritesFlow(): Flow<List<Long>> {
        return appDatabase.favoritesDao().observeFavorites().map { it.map { it.stageId } }
    }

    override suspend fun addStageIdToFavorites(id: Long) {
        appDatabase.favoritesDao().addToFavorites(FavoritesDbEntity(stageId = id))
    }

    override suspend fun deleteFromFavoritesByStageId(id: Long) {
        appDatabase.favoritesDao().deleteFromFavorites(id)
    }
}