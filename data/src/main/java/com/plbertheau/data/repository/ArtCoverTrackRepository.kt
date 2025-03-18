package com.plbertheau.data.repository

import com.plbertheau.data.model.Track

interface ArtCoverTrackRepository {
    suspend fun getArtCoverTracks(): List<Track>
    suspend fun getTracksFromDatabase(): List<Track>
}