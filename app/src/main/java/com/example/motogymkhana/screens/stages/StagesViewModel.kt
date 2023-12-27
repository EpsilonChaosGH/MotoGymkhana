package com.example.motogymkhana.screens.stages

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.R
import com.example.motogymkhana.WhileUiSubscribed
import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.mappers.toStageState
import com.example.motogymkhana.model.SideEffect
import com.example.motogymkhana.model.StageState
import com.example.motogymkhana.model.StagesScreenState
import com.example.motogymkhana.model.Type
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
class StagesViewModel @Inject constructor(
    private val gymkhanaCupRepository: GymkhanaCupRepository
) : ViewModel() {

    private val _stages = MutableStateFlow(listOf<StageState>())
    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<StagesScreenState?> = combine(
        _stages,
        _userMessage,
        _isLoading,
    ) { stages, userMessage, isLoading->
        StagesScreenState(
            stages = stages,
            userMessage = userMessage,
            isLoading = isLoading
        )
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("aaa",exception.message.toString())
        exception.stackTrace
        val result = when (exception) {
            is IOException -> R.string.error
            else -> R.string.error
        }
        showMessage(result)
        setLoading(false)
    }

    init {
        loadStages()
    }

    private fun loadStages() {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            _stages.value = gymkhanaCupRepository.getStageList(Type.Offline.value).sortedBy { it.dateOfThe }.map { it.toStageState() }
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