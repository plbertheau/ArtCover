package com.plbertheau.domain.repository

import com.plbertheau.domain.model.Track

interface ArtCoverTrackRepository {
    suspend fun getArtCoverTracks(): Result<List<Track>>
    suspend fun getTracksFromDatabase(): Result<List<Track>>
}