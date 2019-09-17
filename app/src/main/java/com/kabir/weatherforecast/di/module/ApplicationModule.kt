package com.kabir.weatherforecast.di.module

import androidx.room.Room
import com.kabir.weatherforecast.App
import com.kabir.weatherforecast.data.local.db.DatabaseHelper
import com.kabir.weatherforecast.data.local.prefs.AppPreferences
import com.kabir.weatherforecast.data.remote.ApiHelper
import com.kabir.weatherforecast.utils.network.NetworkHelper
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: App) {

    @Singleton
    @Provides
    fun providesApiInterface() = ApiHelper.getInterface()

    @Provides
    fun providesCompositeDisposable() = CompositeDisposable()

    @Singleton
    @Provides
    fun providesNetworkHelper() = NetworkHelper(application)

    @Singleton
    @Provides
    fun providesDatabaseHelper() = Room.databaseBuilder(
        application, DatabaseHelper::class.java, "room-database"
    ).build()

    @Singleton
    @Provides
    fun providesAppPreferences(): AppPreferences = AppPreferences(application)
}