package com.plbertheau.data.service

import com.google.gson.annotations.SerializedName
import com.plbertheau.domain.model.Track

data class TrackDTO(
    @field:SerializedName("results") val albumId: Int,
    @field:SerializedName("results") val id: Int,
    @field:SerializedName("results") val title: String,
    @field:SerializedName("results") val url: String,
    @field:SerializedName("results") val thumbnailUrl: String
)


fun TrackDTO.toDomain(): Track {
    return Track(id, albumId, title, url, thumbnailUrl)
}