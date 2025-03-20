package com.plbertheau.domain.usecase

import com.plbertheau.domain.model.Track
import com.plbertheau.domain.repository.ArtCoverTrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTracksUseCase @Inject constructor(
    private val repository: ArtCoverTrackRepository
) {
    operator fun invoke(): Flow<Result<List<Track>>> = flow {
        emit(Result.Loading)

        val tracksFromDb = repository.getTracksFromDatabase()
        if (tracksFromDb.isSuccess && tracksFromDb.getOrNull()?.isNotEmpty() == true) {
            emit(Result.Success(tracksFromDb.getOrNull()!!))
            return@flow
        }

        val tracksFromApiResult = repository.getArtCoverTracks()
        if (tracksFromApiResult.isSuccess) {
            emit(Result.Success(tracksFromApiResult.getOrNull()!!))
        } else {
            emit(Result.Error(Exception("Unknown error")))
        }
    }.catch { e ->
        emit(Result.Error(Exception(e)))
    }.flowOn(Dispatchers.IO)

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
        data object Loading : Result<Nothing>()
    }
}