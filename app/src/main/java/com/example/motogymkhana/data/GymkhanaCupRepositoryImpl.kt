package com.example.motogymkhana.data


import android.util.Log
import android.widget.Toast
import com.example.motogymkhana.data.model.getResult
import com.example.motogymkhana.data.model.ChampionshipInfoResponse
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
    override suspend fun getStateInfo(id: String, type: String): StageInfoResponse {
        return gymkhanaService.getStageInfo(id = id, type = type).getResult()
    }

    override suspend fun getStageList(type: String): List<StageResponse> = withContext(Dispatchers.IO) {
        val stagesIdList = mutableListOf<Long>()
        getChampionships(type).map {
            it.stages.map {
                stagesIdList.add(it.id)
            }
        }

        Log.e("aaa","stagesIdList")
        val stages = mutableListOf<StageResponse>()

        stagesIdList.map {
            async {
                gymkhanaService.getStage(
                    id = it.toString(),
                    type = type
                ).getResult().also {
                    Log.e("aaa",it.title)
                }
            }
        }.awaitAll().forEach {
            Log.e("aaa",it.title)
            stages.add(it)
        }
        return@withContext stages
    }

    private suspend fun getChampionships(type: String): List<ChampionshipInfoResponse> =
        withContext(Dispatchers.IO) {

            val idList = gymkhanaService.getChampionshipsIdList(type = type).getResult()
            val championships = mutableListOf<ChampionshipInfoResponse>()

            idList.map {
                async {
                    gymkhanaService.getChampionshipInfo(
                        id = it.id.toString(),
                        type = type
                    ).getResult()
                }
            }.awaitAll().forEach {
                championships.add(it)
            }
            return@withContext championships
        }

}