package com.plbertheau.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plbertheau.domain.model.Track

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

fun TrackEntity.toDomain(): Track {
    return Track(id, albumId, title, url, thumbnailUrl)
}

fun Track.toEntity(): TrackEntity {
    return TrackEntity(id, albumId, title, url, thumbnailUrl)
}