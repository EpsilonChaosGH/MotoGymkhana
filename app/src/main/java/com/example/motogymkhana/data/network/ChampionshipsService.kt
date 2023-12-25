package com.example.motogymkhana.data.network

import com.example.motogymkhana.Const
import com.example.motogymkhana.model.Type
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChampionshipsService {

    @GET("championships/list?")
    suspend fun getChampionshipsInfo(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("type") type: String = Type.Offline.value,
        @Query("fromYear") fromYear: String = Const.fromYear,
        @Query("toYear") toYear: String = Const.toYear,
    ): Response<List<ChampionshipResponse>>
}