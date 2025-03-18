package com.plbertheau.data.repository

import com.plbertheau.data.model.Track
import com.plbertheau.data.room.TrackDao
import com.plbertheau.data.service.ArtCoverApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtCoverTrackRepositoryImpl @Inject constructor(
    private val api: ArtCoverApi,
    private val trackDao: TrackDao
) : ArtCoverTrackRepository {
    override suspend fun getArtCoverTracks(): List<Track> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTrackList()
                trackDao.insertTracks(response)
                response
            } catch (e: Exception) {
                println("exception: $e")
                emptyList()
            }
        }
    }

    override suspend fun getTracksFromDatabase(): List<Track> {
        return withContext(Dispatchers.IO) {
            trackDao.getAllTracks()
        }
    }
}