package com.example.motogymkhana.screens.stages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motogymkhana.R
import com.example.motogymkhana.data.FavoritesRepository
import com.example.motogymkhana.data.GymkhanaCupRepository
import com.example.motogymkhana.mappers.toStageState
import com.example.motogymkhana.model.StageState
import com.example.motogymkhana.utils.SideEffect
import com.example.motogymkhana.utils.WhileUiSubscribed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StagesViewModel @Inject constructor(
    private val gymkhanaCupRepository: GymkhanaCupRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _stages = MutableStateFlow(listOf<StageState>())
    private val _userMessage = MutableStateFlow<SideEffect<Int?>>(SideEffect(null))
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<StagesScreenState?> = combine(
        _stages,
        _userMessage,
        _isLoading,
    ) { stages, userMessage, isLoading ->
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
        exception.stackTrace
        val result = when (exception) {
            is IOException -> R.string.error
            else -> R.string.error
        }
        showMessage(result)
        setLoading(false)
    }

    fun loadStages(championshipId: Long, type: String) {
        viewModelScope.launch() {
            setLoading(true)
            _stages.value =
                gymkhanaCupRepository.getStagesList(champId = championshipId, type = type)
                    .sortedBy { it.dateOfThe }
                    .map { it.toStageState(favoritesRepository.getFavoritesFlow().first()) }
            setLoading(false)
        }
    }

    fun addStageIdToFavorites(id: Long) {
        viewModelScope.launch(exceptionHandler) {
            favoritesRepository.addStageIdToFavorites(id)
            _stages.value =
                _stages.value.map { if (it.stageID == id) it.copy(isFavorites = true) else it.copy() }
        }
    }

    fun deleteFromFavoritesByStageId(id: Long) {
        viewModelScope.launch(exceptionHandler) {
            favoritesRepository.deleteFromFavoritesByStageId(id)
            _stages.value =
                _stages.value.map { if (it.stageID == id) it.copy(isFavorites = false) else it.copy() }
        }
    }

    private fun showMessage(messageRes: Int) {
        _userMessage.value = SideEffect(messageRes)
    }

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }
}