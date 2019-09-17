package com.kabir.weatherforecast.ui.location

import androidx.lifecycle.MutableLiveData
import com.kabir.weatherforecast.data.local.db.DatabaseHelper
import com.kabir.weatherforecast.data.local.prefs.AppPreferences
import com.kabir.weatherforecast.data.remote.ApiInterface
import com.kabir.weatherforecast.ui.base.BaseViewModel
import com.kabir.weatherforecast.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class LocationViewModel(
    networkHelper: NetworkHelper,
    apiInterface: ApiInterface,
    compositeDisposable: CompositeDisposable,
    appPreferences: AppPreferences,
    databaseHelper: DatabaseHelper
) : BaseViewModel(
    networkHelper, apiInterface, compositeDisposable, appPreferences, databaseHelper
) {

    val navigateToWeatherLiveData = MutableLiveData<String>()

    fun start(isFromWeather: Boolean) {
        if (!isFromWeather) {
            val lastInput = appPreferences.getLastSuccessfulInput()
            if (!lastInput.isNullOrBlank()) {
                navigateToWeatherLiveData.postValue(lastInput)
            }
        }
    }

    fun submitInput(input: String) {
        if (input.isNullOrBlank()) {
            errorStringLiveData.postValue("Input cannot be blank")
            return
        }

        navigateToWeatherLiveData.postValue(input)
    }
}