package com.example.motogymkhana.data

import com.example.motogymkhana.data.model.ChampionshipResponse
import com.example.motogymkhana.data.model.StageInfoResponse
import com.example.motogymkhana.data.model.StageResponse

interface GymkhanaCupRepository {

    @Throws
    suspend fun getChampionshipsList(type: String, fromYear: String, toYear: String): List<ChampionshipResponse>

    @Throws
    suspend fun getStageInfo(id: String, type: String): StageInfoResponse

    @Throws
    suspend fun getStagesList(championshipId: Long, type: String): List<StageResponse>

    @Throws
    suspend fun getFavoriteStagesList(type: String, idList: List<Long>): List<StageResponse>
}