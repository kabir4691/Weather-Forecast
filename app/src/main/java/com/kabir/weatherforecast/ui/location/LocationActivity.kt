package com.kabir.weatherforecast.ui.location

import android.os.Bundle
import androidx.lifecycle.Observer
import com.kabir.weatherforecast.R
import com.kabir.weatherforecast.di.component.ActivityComponent
import com.kabir.weatherforecast.ui.base.BaseActivity
import com.kabir.weatherforecast.ui.weather.WeatherActivity
import com.kabir.weatherforecast.utils.launchActivity
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : BaseActivity<LocationViewModel>() {

    companion object {
        const val EXTRA_IS_FROM_WEATHER = "is_from_weather"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.start(intent.getBooleanExtra(EXTRA_IS_FROM_WEATHER, false))
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.navigateToWeatherLiveData.observe(this, Observer {
            val bundle = Bundle()
            bundle.putString(WeatherActivity.EXTRA_INPUT, it)
            launchActivity(WeatherActivity::class.java, extras = bundle, clearStack = true)
            overridePendingTransition(0, 0)
        })
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_location

    override fun setupView() {
        setSupportActionBar(toolbar)

        val isFromWeather = intent.getBooleanExtra(EXTRA_IS_FROM_WEATHER, false)
        if (isFromWeather) {
            supportActionBar!!.run {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        image_forward.setOnClickListener {
            viewModel.submitInput(edit_city.text.toString())
        }
    }
}