package com.example.motogymkhana.data.network

import com.example.motogymkhana.Const
import com.example.motogymkhana.data.model.ChampionshipResponse
import com.example.motogymkhana.data.model.ChampionshipInfoResponse
import com.example.motogymkhana.data.model.StageInfoResponse
import com.example.motogymkhana.data.model.StageResponse
import com.example.motogymkhana.model.Type
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GymkhanaService {

    @GET("championships/list?")
    suspend fun getChampionshipsList(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("type") type: String = Type.Offline.value,
        @Query("fromYear") fromYear: String = Const.fromYear,
        @Query("toYear") toYear: String = Const.toYear,
    ): Response<List<ChampionshipResponse>>

    @GET("championships/get?")
    suspend fun getChampionshipInfo(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("id") id: String,
        @Query("type") type: String,
    ): Response<ChampionshipInfoResponse>

    @GET("stages/get?")
    suspend fun getStage(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("id") id: String,
        @Query("type") type: String,
    ): Response<StageResponse>

    @GET("stages/get?")
    suspend fun getStageInfo(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("id") id: String,
        @Query("type") type: String,
    ): Response<StageInfoResponse>

}