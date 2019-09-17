package com.kabir.weatherforecast.di.component

import com.kabir.weatherforecast.App
import com.kabir.weatherforecast.data.local.db.DatabaseHelper
import com.kabir.weatherforecast.data.local.prefs.AppPreferences
import com.kabir.weatherforecast.data.remote.ApiInterface
import com.kabir.weatherforecast.di.module.ApplicationModule
import com.kabir.weatherforecast.utils.network.NetworkHelper
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: App)

    fun getApiInterface(): ApiInterface

    fun getCompositeDisposable(): CompositeDisposable

    fun getNetworkHelper(): NetworkHelper

    fun getDatabaseHelper(): DatabaseHelper

    fun getAppPreferences(): AppPreferences
}