package com.example.motogymkhana.data

import com.example.motogymkhana.data.network.StageInfoResponse

interface StageRepository {

    @Throws
    suspend fun getStateInfo(id: String, type: String): StageInfoResponse

}