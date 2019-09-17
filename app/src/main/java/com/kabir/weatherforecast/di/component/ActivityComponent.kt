package com.kabir.weatherforecast.di.component

import com.kabir.weatherforecast.di.ActivityScope
import com.kabir.weatherforecast.di.module.ActivityModule
import com.kabir.weatherforecast.ui.location.LocationActivity
import com.kabir.weatherforecast.ui.weather.WeatherActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: WeatherActivity)

    fun inject(activity: LocationActivity)
}