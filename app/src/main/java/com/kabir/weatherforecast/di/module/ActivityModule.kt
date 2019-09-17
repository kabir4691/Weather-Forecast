package com.kabir.weatherforecast.di.module

import androidx.lifecycle.ViewModelProviders
import com.kabir.weatherforecast.data.local.db.DatabaseHelper
import com.kabir.weatherforecast.data.local.prefs.AppPreferences
import com.kabir.weatherforecast.data.remote.ApiInterface
import com.kabir.weatherforecast.ui.base.BaseActivity
import com.kabir.weatherforecast.ui.location.LocationViewModel
import com.kabir.weatherforecast.ui.weather.WeatherViewModel
import com.kabir.weatherforecast.utils.ViewModelProviderFactory
import com.kabir.weatherforecast.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun providesWeatherViewModel(
        networkHelper: NetworkHelper,
        apiInterface: ApiInterface,
        compositeDisposable: CompositeDisposable,
        appPreferences: AppPreferences,
        databaseHelper: DatabaseHelper
    ): WeatherViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(WeatherViewModel::class) {
                WeatherViewModel(
                    networkHelper,
                    apiInterface,
                    compositeDisposable,
                    appPreferences,
                    databaseHelper
                )
            }).get(WeatherViewModel::class.java)

    @Provides
    fun providesLocationViewModel(
        networkHelper: NetworkHelper,
        apiInterface: ApiInterface,
        compositeDisposable: CompositeDisposable,
        appPreferences: AppPreferences,
        databaseHelper: DatabaseHelper
    ): LocationViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(LocationViewModel::class) {
                LocationViewModel(
                    networkHelper,
                    apiInterface,
                    compositeDisposable,
                    appPreferences,
                    databaseHelper
                )
            }).get(LocationViewModel::class.java)
}