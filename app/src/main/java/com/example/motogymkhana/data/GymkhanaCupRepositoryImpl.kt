package com.example.motogymkhana.data


import com.example.motogymkhana.data.model.getResult
import com.example.motogymkhana.data.model.ChampionshipResponse
import com.example.motogymkhana.model.PostTimeRequestBody
import com.example.motogymkhana.data.network.GymkhanaService
import com.example.motogymkhana.data.model.StageResponse
import com.example.motogymkhana.data.model.TimeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymkhanaCupRepositoryImpl @Inject constructor(
    private val gymkhanaService: GymkhanaService
) : GymkhanaCupRepository {
    override suspend fun getChampionshipsList(
        type: String,
        fromYear: String,
        toYear: String
    ): List<ChampionshipResponse> {
        return gymkhanaService.getChampionshipsList(
            type = type,
            fromYear = fromYear,
            toYear = toYear
        ).getResult()
    }

    override suspend fun getStage(id: String, type: String): StageResponse =
        withContext(Dispatchers.IO) {
        return@withContext gymkhanaService.getStage(id = id, type = type).getResult()
    }

    override suspend fun getStagesList(championshipId: Long, type: String): List<StageResponse> =
        withContext(Dispatchers.IO) {
            val stagesIdList = mutableListOf<Long>()

            gymkhanaService.getChampionship(id = championshipId.toString(), type = type)
                .getResult().stages.map {
                stagesIdList.add(it.id)
            }

            val stages = mutableListOf<StageResponse>()
            stagesIdList.map {
                async { gymkhanaService.getStage(id = it.toString(), type = type).getResult() }
            }.awaitAll().forEach { stages.add(it) }
            return@withContext stages
        }

    override suspend fun getFavoriteStagesList(type: String, idList: List<Long>): List<StageResponse> =
        withContext(Dispatchers.IO) {
            val stages = mutableListOf<StageResponse>()

            idList.map {
                async { gymkhanaService.getStage(id = it.toString(), type = type).getResult() }
            }.awaitAll().forEach { stages.add(it) }
            return@withContext stages
        }

    override suspend fun postTime(postTimeRequestBody: PostTimeRequestBody): Response<TimeResponse> {
        val fields: HashMap<String?, RequestBody?> = HashMap()

        with(postTimeRequestBody) {
            fields["stageId"] = stageId.toRequestBody("text/plain".toMediaTypeOrNull())
            fields["participantId"] = participantID.toRequestBody("text/plain".toMediaTypeOrNull())
            fields["attempt"] = attempt.toRequestBody("text/plain".toMediaTypeOrNull())
            fields["time"] = time.toRequestBody("text/plain".toMediaTypeOrNull())
            fields["fine"] = fine.toRequestBody("text/plain".toMediaTypeOrNull())
            fields["isFail"] = isFail.toRequestBody("text/plain".toMediaTypeOrNull())
        }

        return gymkhanaService.postTime(map = fields)
    }
}