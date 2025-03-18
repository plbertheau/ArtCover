package com.plbertheau.data.service

import com.plbertheau.data.model.Track
import retrofit2.http.GET

interface ArtCoverApi {

    @GET("img/shared/technical-test.json")
    suspend fun getTrackList(): List<Track>
}