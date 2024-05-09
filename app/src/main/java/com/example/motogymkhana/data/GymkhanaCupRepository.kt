package com.example.motogymkhana.data

import com.example.motogymkhana.data.model.ChampionshipResponse
import com.example.motogymkhana.model.PostTimeRequestBody
import com.example.motogymkhana.data.model.StageResponse
import com.example.motogymkhana.data.model.TimeResponse
import retrofit2.Response

interface GymkhanaCupRepository {

    @Throws
    suspend fun getChampionshipsList(type: String, fromYear: String, toYear: String): List<ChampionshipResponse>

    @Throws
    suspend fun getStage(id: String, type: String): StageResponse

    @Throws
    suspend fun getStagesList(champId: Long, type: String): List<StageResponse>

    @Throws
    suspend fun getFavoriteStagesList(type: String, idList: List<Long>): List<StageResponse>

    @Throws
    suspend fun postTime(postTimeRequestBody: PostTimeRequestBody): Response<TimeResponse>
}