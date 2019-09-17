package com.kabir.weatherforecast.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kabir.weatherforecast.data.local.db.entity.ForecastData

class Converters {

    @TypeConverter
    fun listToJson(value: List<ForecastData>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<ForecastData>? {
        val objects = Gson().fromJson(value, Array<ForecastData>::class.java) as Array<ForecastData>
        return objects.toList()
    }
}