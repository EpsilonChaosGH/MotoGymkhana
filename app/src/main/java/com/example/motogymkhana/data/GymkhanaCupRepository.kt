package com.example.motogymkhana.data

import com.example.motogymkhana.data.network.ChampionshipResponse
import com.example.motogymkhana.data.network.StageInfoResponse

interface GymkhanaCupRepository {

    @Throws
    suspend fun getStateInfo(id: String, type: String): StageInfoResponse

    @Throws
    suspend fun getChampionships(type: String): List<ChampionshipResponse>
}