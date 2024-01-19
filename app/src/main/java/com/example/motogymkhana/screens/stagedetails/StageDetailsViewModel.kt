package com.example.motogymkhana.screens.stagedetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.R
import com.example.motogymkhana.utils.WhileUiSubscribed
import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.mappers.toStageInfoState
import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.model.StageInfoState
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
    private val gymkhanaCupRepository: GymkhanaCupRepository
) : ViewModel() {

    private val _stageInfo = MutableStateFlow<StageInfoState?>(null)
    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<StagesDetailsScreenState?> = combine(
        _stageInfo,
        _userMessage,
        _isLoading,
    ) { stageInfo, userMessage, isLoading->
        StagesDetailsScreenState(
            stageInfo  = stageInfo,
            userMessage = userMessage,
            isLoading = isLoading
        )
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.stackTrace
        Log.e("aaaerror",exception.message.toString())
        val result = when (exception) {
            is IOException -> R.string.error
            else -> R.string.error
        }
        showMessage(result)
        setLoading(false)
    }

    fun getStageInfo(id: String, type: String) {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            _stageInfo.value = gymkhanaCupRepository.getStageInfo(id, type).toStageInfoState()
//            gymkhanaCupRepository.getStageInfo(id, type).results.map {
//                it.attemtps.map {
//                    it.time
//                }
//            }
//            gymkhanaCupRepository.getStageInfo(id, type).results.map {
//                it.attemtps.size
//                it.attemtps.map {
//                    it.time
//                    Log.e("aaa",it.time)
//                }
//            }
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