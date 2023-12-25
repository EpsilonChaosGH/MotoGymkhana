package com.example.motogymkhana.data


import com.example.motogymkhana.data.network.ChampionshipResponse
import com.example.motogymkhana.data.network.ChampionshipsService
import com.example.motogymkhana.data.network.StageInfoResponse
import com.example.motogymkhana.data.network.StagesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymkhanaCupRepositoryImpl @Inject constructor(
    private val stagesService: StagesService,
    private val championshipsService: ChampionshipsService
) : GymkhanaCupRepository {
    override suspend fun getStateInfo(id: String, type: String): StageInfoResponse {
          return stagesService.getStageInfo(id = id, type = type).getResult()
    }

    override suspend fun getChampionships(type: String): List<ChampionshipResponse> {
        return championshipsService.getChampionshipsInfo(type = type).getResult()
    }
}