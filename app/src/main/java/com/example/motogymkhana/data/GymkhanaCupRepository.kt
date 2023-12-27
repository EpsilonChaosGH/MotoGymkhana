package com.example.motogymkhana.data

import com.example.motogymkhana.data.model.ChampionshipIdResponse
import com.example.motogymkhana.data.model.ChampionshipInfoResponse
import com.example.motogymkhana.data.model.StageInfoResponse
import com.example.motogymkhana.data.model.StageResponse

interface GymkhanaCupRepository {

    @Throws
    suspend fun getStateInfo(id: String, type: String): StageInfoResponse

    @Throws
    suspend fun getStageList(type: String): List<StageResponse>

//    @Throws
//    suspend fun getChampionships(type: String): List<ChampionshipInfoResponse>
}