package com.plbertheau.artcover.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plbertheau.data.model.TrackResponse
import com.plbertheau.data.repository.ArtCoverTrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(private val repository: ArtCoverTrackRepository) : ViewModel() {
    val tracks = mutableStateOf<List<TrackResponse>>(emptyList())

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            val data = repository.getArtCoverTracks()
            tracks.value = data
        }
    }
}