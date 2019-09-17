package com.kabir.weatherforecast.data.remote

import com.kabir.weatherforecast.data.remote.response.GetForecastResponse
import com.kabir.weatherforecast.data.remote.response.GetWeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getWeather(@Query("appid") appId: String, @Query("q") input: String): Observable<GetWeatherResponse>

    @GET("forecast/daily")
    fun getForecast(@Query("appid") appId: String, @Query("q") input: String): Observable<GetForecastResponse>
}