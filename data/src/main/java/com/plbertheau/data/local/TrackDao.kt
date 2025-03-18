package com.plbertheau.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<TrackEntity>)

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM tracks")
    fun getAllTracks(): List<TrackEntity>
}
