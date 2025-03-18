package com.plbertheau.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plbertheau.data.model.Track

@Database(
    entities = [Track::class],
    version = 2, exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(Converters::class)
abstract class TrackLocalDB : RoomDatabase() {
    abstract fun getTrackDao(): TrackDao
}
