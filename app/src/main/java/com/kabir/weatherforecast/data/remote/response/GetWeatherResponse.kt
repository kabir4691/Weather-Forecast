package com.kabir.weatherforecast.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetWeatherResponse(
    @Expose
    @SerializedName("weather")
    val weatherList: List<Weather>,

    @Expose
    @SerializedName("main")
    val main: Main,

    @Expose
    @SerializedName("name")
    val cityName: String
)

data class Weather(
    @Expose
    @SerializedName("main")
    val group: String,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("icon")
    val iconId: String
)

data class Main(
    @Expose
    @SerializedName("temp")
    val temperature: Float,

    @Expose
    @SerializedName("temp_min")
    val minimumTemperature: Float,

    @Expose
    @SerializedName("temp_max")
    val maximumTemperature: Float
)