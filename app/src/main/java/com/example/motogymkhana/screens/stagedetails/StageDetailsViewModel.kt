package com.example.motogymkhana.screens.stagedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.R
import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.model.PostTimeRequestBody
import com.example.motogymkhana.mappers.toStageState
import com.example.motogymkhana.model.IsActive
import com.example.motogymkhana.model.StageState
import com.example.motogymkhana.model.Type
import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StageDetailsViewModel @Inject constructor(
    private val gymkhanaCupRepository: GymkhanaCupRepository,
) : ViewModel() {

    private val _stage = MutableStateFlow<StageState?>(null)
    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<StageDetailsScreenState?> = combine(
        _stage,
        _userMessage,
        _isLoading,
    ) { stageInfo, userMessage, isLoading ->
        StageDetailsScreenState(
            stageState = stageInfo,
            userMessage = userMessage,
            isLoading = isLoading
        )
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("aaa_StageDetailsViewModel", exception.message.toString())
        val result = when (exception) {
            is IOException -> R.string.error_io
            else -> R.string.error
        }
        showMessage(result)
        setLoading(false)
    }

    fun setActive(isActive: IsActive, participantID: Long) {
        _stage.value?.let {
            _stage.value = _stage.value?.copy(results = it.results.map { user ->
                if (user.participantID == participantID) {
                    user.copy(isActive = isActive)
                } else {
                    user.copy(isActive = IsActive.INACTIVE)
                }
            })
        }
    }

    fun postTime(requestBody: PostTimeRequestBody) {
        viewModelScope.launch(exceptionHandler) {
            val response = gymkhanaCupRepository.postTime(requestBody)
            if (response.isSuccessful) {
                _stage.value?.stageID?.let {
                    getStageInfo(id = it.toString(), type = Type.Offline.value)
                }
            } else {
                throw IOException()
            }
        }
    }

    fun refresh() {
        _stage.value?.stageID?.let {
            getStageInfo(id = it.toString(), type = Type.Offline.value)
        }
    }

    fun getStageInfo(id: String, type: String) {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            _stage.value = gymkhanaCupRepository.getStage(id, type).toStageState()
            setLoading(false)
        }
    }

    private fun showMessage(messageRes: Int) {
        _userMessage.value = SideEffect(messageRes)
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}