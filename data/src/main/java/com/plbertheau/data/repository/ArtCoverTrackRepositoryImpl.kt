package com.plbertheau.data.repository

import com.plbertheau.domain.model.Track
import com.plbertheau.data.local.TrackDao
import com.plbertheau.data.local.toDomain
import com.plbertheau.data.local.toEntity
import com.plbertheau.data.service.ArtCoverApi
import com.plbertheau.data.service.toDomain
import com.plbertheau.domain.repository.ArtCoverTrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtCoverTrackRepositoryImpl @Inject constructor(
    private val api: ArtCoverApi,
    private val trackDao: TrackDao
) : ArtCoverTrackRepository {
    /**
     * Since my API function is a suspend function, it will run in whatever coroutine context calls it.
     * If that context is not Dispatchers.IO, the network call could block the main thread.
     */
    override suspend fun getArtCoverTracks(): Result<List<Track>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getTrackList().map { it.toDomain() }
                trackDao.insertTracks(response.map { it.toEntity() })
                Result.success(response)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getTracksFromDatabase(): Result<List<Track>> {
        return Result.success(trackDao.getAllTracks().map { it.toDomain() })
    }
}