package com.plbertheau.data.repository

import com.plbertheau.domain.model.Track
import com.plbertheau.data.local.TrackDao
import com.plbertheau.data.local.toDomain
import com.plbertheau.data.local.toEntity
import com.plbertheau.data.service.ArtCoverApi
import com.plbertheau.data.service.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtCoverTrackRepositoryImpl @Inject constructor(
    private val api: ArtCoverApi,
    private val trackDao: TrackDao
) : com.plbertheau.domain.repository.ArtCoverTrackRepository {
    override suspend fun getArtCoverTracks(): List<Track> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTrackList().map { it.toDomain() }
                trackDao.insertTracks(response.map { it.toEntity() })
                response
            } catch (e: Exception) {
                println("exception: $e")
                emptyList()
            }
        }
    }

    override suspend fun getTracksFromDatabase(): List<Track> {
        return withContext(Dispatchers.IO) {
            trackDao.getAllTracks().map { it.toDomain() }
        }
    }
}