package com.example.motogymkhana.screens.stage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.R
import com.example.motogymkhana.data.StageRepository
import com.example.motogymkhana.mappers.toStageInfoState
import com.example.motogymkhana.model.SideEffect
import com.example.motogymkhana.model.StageInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StageViewModel @Inject constructor(
    private val stageRepository: StageRepository
) : ViewModel() {

    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    val userMessage = _userMessage.asStateFlow()
    private val _isLoading = MutableStateFlow(false)

    private val _state = MutableStateFlow<StageInfoState?>(null)
    val state = _state.asStateFlow()


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.stackTrace
        val result = when (exception) {
            is IOException -> R.string.error
            else -> R.string.error
        }
        showMessage(result)
        setLoading(false)
    }



    fun getStateInfo() {
        viewModelScope.launch(exceptionHandler) {
           _state.value = stageRepository.getStateInfo(
                id = "270",
                type = "offline"
            ).toStageInfoState()
        }
    }

    private fun showMessage(messageRes: Int) {
        _userMessage.value = SideEffect(messageRes)
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}