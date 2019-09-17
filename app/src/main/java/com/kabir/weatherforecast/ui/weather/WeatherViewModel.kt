package com.kabir.weatherforecast.ui.weather

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.kabir.weatherforecast.data.local.db.DatabaseHelper
import com.kabir.weatherforecast.data.local.db.entity.ForecastData
import com.kabir.weatherforecast.data.local.db.entity.WeatherEntity
import com.kabir.weatherforecast.data.local.prefs.AppPreferences
import com.kabir.weatherforecast.data.remote.ApiInterface
import com.kabir.weatherforecast.data.remote.response.GetForecastResponse
import com.kabir.weatherforecast.data.remote.response.GetWeatherResponse
import com.kabir.weatherforecast.ui.base.BaseViewModel
import com.kabir.weatherforecast.utils.kelvinToCelsius
import com.kabir.weatherforecast.utils.network.NetworkHelper
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherViewModel(
    networkHelper: NetworkHelper,
    apiInterface: ApiInterface,
    compositeDisposable: CompositeDisposable,
    appPreferences: AppPreferences,
    databaseHelper: DatabaseHelper
) : BaseViewModel(
    networkHelper, apiInterface, compositeDisposable, appPreferences,
    databaseHelper
) {

    val weatherDataLiveData = MutableLiveData<WeatherEntity>()
    private var savedInput: String = ""
    private val refreshHandler: Handler by lazy {
        Handler()
    }
    private val REFRESH_INTERVAL: Long = 1000 * 60 * 5

    override fun onCleared() {
        super.onCleared()
        refreshHandler.removeCallbacksAndMessages(null)
    }

    fun start(input: String) {
        savedInput = input
        compositeDisposable.add(
            databaseHelper.weatherDao().getByInput(input)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    weatherDataLiveData.postValue(it)
                }, {
                    Timber.w(it)
                })
        )

        loadData(input)
    }

    fun reload(): Boolean {
        return if (savedInput.isBlank()) {
            false
        } else {
            loadData(savedInput)
            true
        }
    }

    private fun loadData(input: String) {
        if (!checkInternetConnectionWithError()) {
            return
        }

        val weatherObservable = apiInterface.getWeather(appPreferences.getApiKey()!!, input)
            .subscribeOn(Schedulers.io())
        val forecastObservable = apiInterface.getForecast(appPreferences.getApiKey()!!, input)
            .subscribeOn(Schedulers.io())
        compositeDisposable.add(
            Observable.zip(weatherObservable, forecastObservable,
                BiFunction<GetWeatherResponse, GetForecastResponse, WeatherEntity> { weatherResponse, forecastResponse ->
                    return@BiFunction generateWeatherData(input, weatherResponse, forecastResponse)
                })
                .subscribeOn(Schedulers.io())
                .subscribe({ weatherEntity ->
                    weatherDataLiveData.postValue(weatherEntity)
                    appPreferences.setLastSuccessfulInput(weatherEntity.city)
                    databaseHelper.weatherDao().insert(weatherEntity)
                        .flatMap {
                            if (it == -1L) {
                                databaseHelper.weatherDao().update(weatherEntity)
                            } else {
                                Single.just(0)
                            }
                        }
                        .subscribeOn(Schedulers.io())
                        .subscribe({

                        }, {
                            Timber.w(it)
                        })
                    refreshHandler.postDelayed({
                        refresh()
                    }, REFRESH_INTERVAL)
                }, { throwable ->
                    throwable.message?.let {
                        errorStringLiveData.postValue(it)
                    }
                })

        )
    }

    private fun refresh() {
        if (!checkInternetConnection() || savedInput.isBlank()) {
            refreshHandler.postDelayed({
                refresh()
            }, REFRESH_INTERVAL)
            return
        }
        loadData(savedInput)
    }

    private fun generateWeatherData(
        input: String,
        getWeatherResponse: GetWeatherResponse,
        getForecastResponse: GetForecastResponse
    ): WeatherEntity {

        val forecastDataList = ArrayList<ForecastData>()
        val dateFormat = SimpleDateFormat("EEE dd")
        for (i in 0 until 5) {
            val forecast = getForecastResponse.list[i]
            val date = Date(forecast.date)
            val dateString = dateFormat.format(date)
            forecastDataList.add(
                ForecastData(
                    dateString,
                    forecast.temperature.maximum.kelvinToCelsius().toInt(),
                    forecast.temperature.minimum.kelvinToCelsius().toInt(),
                    "http://openweathermap.org/img/w/" + forecast.weatherList[0].iconId + ".png"
                )
            )
        }

        val weather = getWeatherResponse.weatherList[0]
        val main = getWeatherResponse.main

        return WeatherEntity(
            input = input,
            city = getWeatherResponse.cityName,
            groupDescription = weather.description,
            groupIconUrl = "http://openweathermap.org/img/w/" + weather.iconId + ".png",
            currentTemperature = main.temperature.kelvinToCelsius().toInt(),
            maxTemperature = main.maximumTemperature.kelvinToCelsius().toInt(),
            minTemperature = main.minimumTemperature.kelvinToCelsius().toInt(),
            forecastDataList = forecastDataList
        )
    }
}