package com.example.motogymkhana.data

import com.example.motogymkhana.data.model.StageInfoResponse
import com.example.motogymkhana.data.model.StageResponse

interface GymkhanaCupRepository {

    @Throws
    suspend fun getStageInfo(id: String, type: String): StageInfoResponse

    @Throws
    suspend fun getStagesList(type: String): List<StageResponse>

    @Throws
    suspend fun getFavoriteStagesList(type: String, ids: List<Long>): List<StageResponse>

//    @Throws
//    suspend fun getChampionships(type: String): List<ChampionshipInfoResponse>
}