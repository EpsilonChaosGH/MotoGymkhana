package com.example.motogymkhana.data.network

import com.example.motogymkhana.Const
import com.example.motogymkhana.data.model.ChampionshipResponse
import com.example.motogymkhana.data.model.StageResponse
import com.example.motogymkhana.data.model.TimeResponse
import com.example.motogymkhana.model.Type
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap
import retrofit2.http.Query

interface GymkhanaService {

    @GET("championships/list?")
    suspend fun getChampionshipsList(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("type") type: String = Type.Offline.value,
        @Query("fromYear") fromYear: String = Const.FORM_YEAR,
        @Query("toYear") toYear: String = Const.TO_YEAR,
    ): Response<List<ChampionshipResponse>>

    @GET("championships/get?")
    suspend fun getChampionship(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("id") id: String,
        @Query("type") type: String,
    ): Response<ChampionshipResponse>

    @GET("stages/get?")
    suspend fun getStage(
        @Query("signature") signature: String = Const.APP_ID,
        @Query("id") id: String,
        @Query("type") type: String,
    ): Response<StageResponse>

    @Multipart
    @POST("stages/add-time?")
    suspend fun postTime(
        @Query("signature") signature: String = Const.APP_ID,
        @PartMap map: HashMap<String?, RequestBody?>
    ): Response<TimeResponse>
}