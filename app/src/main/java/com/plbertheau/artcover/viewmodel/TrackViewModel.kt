package com.plbertheau.artcover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plbertheau.artcover.model.TrackUiModel
import com.plbertheau.artcover.model.toUiModel
import com.plbertheau.domain.usecase.GetTracksUseCase
import com.plbertheau.domain.usecase.GetTracksUseCase.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(private val getTracksUseCase: GetTracksUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<TrackUiModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<TrackUiModel>>> = _uiState.asStateFlow()

    init {
        fetchTracks()
    }

    fun fetchTracks() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getTracksUseCase.invoke()
                .collect { result ->
                    _uiState.value = when (result) {
                        is Result.Success -> {
                            UiState.Success(result.data.map { it.toUiModel() })
                        }
                        is Result.Error -> {
                            UiState.Error("Erreur : ${result.exception.message ?: "Unknown error"}")
                        }
                        is Result.Loading -> UiState.Loading
                    }
                }
        }
    }
}

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}