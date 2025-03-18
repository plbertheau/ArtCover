package com.plbertheau.domain.usecase

import com.plbertheau.domain.model.Track
import com.plbertheau.domain.repository.ArtCoverTrackRepository
import javax.inject.Inject

class GetTracksUseCase @Inject constructor(
    private val repository: ArtCoverTrackRepository
) {
    suspend fun execute(): List<Track> {
        val tracksFromDb = repository.getTracksFromDatabase()
        return tracksFromDb.ifEmpty {
            repository.getArtCoverTracks()
        }
    }
}