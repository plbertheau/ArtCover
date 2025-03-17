package com.plbertheau.data.repository

import com.plbertheau.data.model.TrackResponse

interface ArtCoverTrackRepository {
    suspend fun getArtCoverTracks(): List<TrackResponse>
}