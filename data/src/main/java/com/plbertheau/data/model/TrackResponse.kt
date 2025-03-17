package com.plbertheau.data.model

data class TrackResponse(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)