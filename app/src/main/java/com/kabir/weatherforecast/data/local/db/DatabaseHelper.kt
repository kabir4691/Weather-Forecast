package com.kabir.weatherforecast.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kabir.weatherforecast.data.local.db.dao.WeatherDao
import com.kabir.weatherforecast.data.local.db.entity.WeatherEntity


@Database(
    entities = [
        WeatherEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}
