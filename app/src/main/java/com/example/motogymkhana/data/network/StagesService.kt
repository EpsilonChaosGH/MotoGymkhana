package com.example.motogymkhana.data.network

import com.example.motogymkhana.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StagesService {

    @GET("stages/get?")
    suspend fun getStageInfo(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("id") id: String,
        @Query("type") type: String,
    ): Response<StageInfoResponse>
}