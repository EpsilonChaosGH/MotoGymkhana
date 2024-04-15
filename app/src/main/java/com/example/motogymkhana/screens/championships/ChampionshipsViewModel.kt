package com.example.motogymkhana.screens.championships

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.R
import com.example.motogymkhana.utils.WhileUiSubscribed
import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.mappers.toChampionshipsState
import com.example.motogymkhana.model.ChampionshipState
import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.model.Type
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
class ChampionshipsViewModel @Inject constructor(
    private val gymkhanaCupRepository: GymkhanaCupRepository
) : ViewModel() {

    private val _stages = MutableStateFlow(listOf<ChampionshipState>())
    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<ChampionshipsScreenState?> = combine(
        _stages,
        _userMessage,
        _isLoading,
    ) { championships, userMessage, isLoading ->
        ChampionshipsScreenState(
            championships = championships,
            userMessage = userMessage,
            isLoading = isLoading
        )
    }.stateIn(
        viewModelScope,
        WhileUiSubscribed,
        null
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("aaa", exception.message.toString())
        exception.stackTrace
        val result = when (exception) {
            is IOException -> R.string.error
            else -> R.string.error
        }
        showMessage(result)
        setLoading(false)
    }

    init {
        loadChampionships()
    }

    private fun loadChampionships() {
        viewModelScope.launch(exceptionHandler) {
            setLoading(true)
            _stages.value =
                gymkhanaCupRepository.getChampionshipsList(Type.Offline.value, "2020", "2024")
                    .sortedBy { it.id }
                    .map { it.toChampionshipsState() }
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