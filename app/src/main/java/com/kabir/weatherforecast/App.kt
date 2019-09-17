package com.kabir.weatherforecast

import androidx.multidex.MultiDexApplication
import com.kabir.weatherforecast.data.local.prefs.AppPreferences
import com.kabir.weatherforecast.di.component.ApplicationComponent
import com.kabir.weatherforecast.di.component.DaggerApplicationComponent
import com.kabir.weatherforecast.di.module.ApplicationModule
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : MultiDexApplication() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        getDependencies()
        initTimber()

        AppPreferences(this).setApiKey("057d8e4caf77c78ec5a4c48fa716bb4b")
    }

    private fun getDependencies() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}