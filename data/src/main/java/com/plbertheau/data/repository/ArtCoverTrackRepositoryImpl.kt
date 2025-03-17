package com.plbertheau.data.repository

import com.plbertheau.data.model.TrackResponse
import com.plbertheau.data.service.ArtCoverApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtCoverTrackRepositoryImpl@Inject constructor(private val api: ArtCoverApi): ArtCoverTrackRepository {
    override suspend fun getArtCoverTracks(): List<TrackResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTrackList()
                response
            } catch (e: Exception) {
                println("exception: $e")
                emptyList()
            }
        }
    }
}