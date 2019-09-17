package com.kabir.weatherforecast.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetForecastResponse(
    @Expose
    @SerializedName("list")
    val list: List<Forecast>
)

data class Forecast(
    @Expose
    @SerializedName("dt")
    val date: Long,

    @Expose
    @SerializedName("temp")
    val temperature: Temperature,

    @Expose
    @SerializedName("weather")
    val weatherList: List<Weather>
)

data class Temperature(

    @Expose
    @SerializedName("min")
    val minimum: Float,

    @Expose
    @SerializedName("max")
    val maximum: Float
)