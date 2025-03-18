package com.plbertheau.domain.repository

import com.plbertheau.domain.model.Track

interface ArtCoverTrackRepository {
    suspend fun getArtCoverTracks(): List<Track>
    suspend fun getTracksFromDatabase(): List<Track>
}