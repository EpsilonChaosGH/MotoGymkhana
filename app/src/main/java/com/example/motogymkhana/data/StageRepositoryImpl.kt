package com.example.motogymkhana.data


import com.example.motogymkhana.data.network.StageInfoResponse
import com.example.motogymkhana.data.network.StagesService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StageRepositoryImpl @Inject constructor(
    private val stagesService: StagesService
) : StageRepository {
    override suspend fun getStateInfo(id: String, type: String): StageInfoResponse {
          return stagesService.getStageInfo(id = id, type = type).getResult()
    }
}