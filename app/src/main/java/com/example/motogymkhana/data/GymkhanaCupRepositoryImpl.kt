package com.example.motogymkhana.data


import com.example.motogymkhana.data.model.getResult
import com.example.motogymkhana.data.model.ChampionshipResponse
import com.example.motogymkhana.data.network.GymkhanaService
import com.example.motogymkhana.data.model.StageInfoResponse
import com.example.motogymkhana.data.model.StageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymkhanaCupRepositoryImpl @Inject constructor(
    private val gymkhanaService: GymkhanaService
) : GymkhanaCupRepository {
    override suspend fun getChampionshipsList(
        type: String,
        fromYear: String,
        toYear: String
    ): List<ChampionshipResponse> {
        return gymkhanaService.getChampionshipsList(
            type = type,
            fromYear = fromYear,
            toYear = toYear
        ).getResult()
    }

    override suspend fun getStageInfo(id: String, type: String): StageInfoResponse =
        withContext(Dispatchers.IO) {
        return@withContext gymkhanaService.getStageInfo(id = id, type = type).getResult()
    }

    override suspend fun getStagesList(championshipId: Long, type: String): List<StageResponse> =
        withContext(Dispatchers.IO) {
            val stagesIdList = mutableListOf<Long>()

            gymkhanaService.getChampionshipInfo(id = championshipId.toString(), type = type)
                .getResult().stages.map {
                stagesIdList.add(it.id)
            }

            val stages = mutableListOf<StageResponse>()
            stagesIdList.map {
                async { gymkhanaService.getStage(id = it.toString(), type = type).getResult() }
            }.awaitAll().forEach { stages.add(it) }
            return@withContext stages
        }

    override suspend fun getFavoriteStagesList(type: String, idList: List<Long>): List<StageResponse> =
        withContext(Dispatchers.IO) {
            val stages = mutableListOf<StageResponse>()

            idList.map {
                async { gymkhanaService.getStage(id = it.toString(), type = type).getResult() }
            }.awaitAll().forEach { stages.add(it) }
            return@withContext stages
        }
}