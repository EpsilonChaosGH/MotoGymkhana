package com.example.motogymkhana.data

import android.util.Log
import com.example.motogymkhana.screens.settings.SettingsSharedPref
import com.example.motogymkhana.utils.getResult
import com.example.motogymkhana.utils.millisecondToMinutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class StopWatchService @Inject constructor(
    private var client: OkHttpClient,
    private val settings: SettingsSharedPref
) {
    private var request: Request = Request.Builder()
        .url("http://${settings.getControllerIp()}/${settings.getRequest()}")
        .build()

    fun getTimeFlow(): Flow<String> {
        return flow {
            while (true) {
                try {
                    emit(getTimeRequest())
                   // emit(Random.nextInt(1000).toString())
                } catch (e: Exception) {
                    Log.e("aaa", e.message.toString())
                }
                delay(settings.getRefreshTime())
            }
        }
    }

    private fun getTimeRequest(): String {
        val response = client.newCall(request).execute()
        return response.getResult().toDouble().millisecondToMinutes()
    }
}