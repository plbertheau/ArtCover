package com.plbertheau.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TrackEntity::class],
    version = 1, exportSchema = true
)
@TypeConverters(Converters::class)
abstract class TrackLocalDB : RoomDatabase() {
    abstract fun getTrackDao(): TrackDao
}
