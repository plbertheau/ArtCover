package com.plbertheau.data.service

import com.google.gson.annotations.SerializedName
import com.plbertheau.domain.model.Track

data class TrackDTO(
    @field:SerializedName("albumId") val albumId: Int,
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("url") val url: String,
    @field:SerializedName("thumbnailUrl") val thumbnailUrl: String
)


fun TrackDTO.toDomain(): Track {
    return Track(id, albumId, title, url, thumbnailUrl)
}