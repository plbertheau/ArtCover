package com.plbertheau.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plbertheau.data.service.TrackDTO

class Converters {
    @TypeConverter
    fun fromTrackList(value: List<TrackDTO>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toTrackList(value: String): List<TrackDTO> {
        val listType = object : TypeToken<List<TrackDTO>>() {}.type
        return Gson().fromJson(value, listType)
    }
}