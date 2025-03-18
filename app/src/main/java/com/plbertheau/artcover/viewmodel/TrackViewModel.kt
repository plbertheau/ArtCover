package com.plbertheau.artcover.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plbertheau.data.model.Track
import com.plbertheau.data.repository.ArtCoverTrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackViewModel @Inject constructor(private val repository: ArtCoverTrackRepository) : ViewModel() {
    val tracks = mutableStateOf<List<Track>>(emptyList())

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            val data = repository.getTracksFromDatabase()
            tracks.value = data.ifEmpty { repository.getArtCoverTracks() }
        }
    }
}