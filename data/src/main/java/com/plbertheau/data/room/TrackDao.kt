package com.plbertheau.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.plbertheau.data.model.Track

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<Track>)

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM tracks")
    fun getAllTracks(): List<Track>
}
