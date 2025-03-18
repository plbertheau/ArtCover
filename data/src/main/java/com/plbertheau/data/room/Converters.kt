package com.plbertheau.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plbertheau.data.model.Track

class Converters {
    @TypeConverter
    fun fromTrackList(value: List<Track>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toTrackList(value: String): List<Track> {
        val listType = object : TypeToken<List<Track>>() {}.type
        return Gson().fromJson(value, listType)
    }
}