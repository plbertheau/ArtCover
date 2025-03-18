package com.plbertheau.artcover.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plbertheau.artcover.model.TrackUiModel
import com.plbertheau.artcover.model.toUiModel
import com.plbertheau.domain.usecase.GetTracksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(private val getTracksUseCase: GetTracksUseCase) : ViewModel() {
    val tracks = mutableStateOf<List<TrackUiModel>>(emptyList())

    init {
        fetchTrack()
    }

    private fun fetchTrack() {
        viewModelScope.launch {
            val data = getTracksUseCase.execute()
            tracks.value = data.map { it.toUiModel() }
        }
    }
}