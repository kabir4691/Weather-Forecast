package com.kabir.weatherforecast.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "weather_entity")
data class WeatherEntity(

    @PrimaryKey
    @ColumnInfo(name = "input")
    @NotNull
    var input: String,

    @ColumnInfo(name = "city")
    @NotNull
    var city: String,

    @ColumnInfo(name = "groupDescription")
    @NotNull
    var groupDescription: String,

    @ColumnInfo(name = "groupIconUrl")
    @NotNull
    var groupIconUrl: String,

    @ColumnInfo(name = "currentTemperature")
    @NotNull
    var currentTemperature: Int,

    @ColumnInfo(name = "maxTemperature")
    @NotNull
    var maxTemperature: Int,

    @ColumnInfo(name = "minTemperature")
    @NotNull
    var minTemperature: Int,

    @ColumnInfo(name = "forecastDataList")
    @NotNull
    var forecastDataList: List<ForecastData>
)

data class ForecastData(
    var day: String,
    var maxTemperature: Int,
    var minTemperature: Int,
    var groupIconUrl: String
)