package com.plbertheau.artcover.model

import com.plbertheau.domain.model.Track

data class TrackUiModel(
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

fun Track.toUiModel(): TrackUiModel {
    return TrackUiModel(id, albumId, title, url, thumbnailUrl)
}