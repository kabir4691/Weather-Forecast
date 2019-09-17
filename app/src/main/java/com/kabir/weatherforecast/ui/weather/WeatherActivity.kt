package com.kabir.weatherforecast.ui.weather

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kabir.weatherforecast.R
import com.kabir.weatherforecast.data.local.db.entity.ForecastData
import com.kabir.weatherforecast.di.component.ActivityComponent
import com.kabir.weatherforecast.ui.base.BaseActivity
import com.kabir.weatherforecast.ui.location.LocationActivity
import com.kabir.weatherforecast.utils.launchActivity
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : BaseActivity<WeatherViewModel>() {

    companion object {

        const val EXTRA_INPUT = "input"
    }

    private val forecastAdapter: ForecastAdapter by lazy {
        ForecastAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val input = intent.getStringExtra(EXTRA_INPUT)
        viewModel.start(input)
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.weatherDataLiveData.observe(this, Observer {
            if (swipe_container.isRefreshing) {
                swipe_container.isRefreshing = false
            }
            text_city.text = it.city
            runOnUiThread {
                Glide.with(this)
                    .load(it.groupIconUrl)
                    .into(image_weather_status)
            }
            text_temp_current.text = it.currentTemperature.toString() + getString(R.string.degree)
            text_temp_max.text = it.maxTemperature.toString() + getString(R.string.degree)
            text_temp_min.text = it.minTemperature.toString() + getString(R.string.degree)
            text_temp_desc.text = it.groupDescription

            forecastAdapter.updateData(it.forecastDataList as ArrayList<ForecastData>)
        })
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun provideLayoutId(): Int = R.layout.activity_weather

    override fun setupView() {
        swipe_container.setOnRefreshListener {
            if (!viewModel.reload()) {
                if (swipe_container.isRefreshing) {
                    swipe_container.isRefreshing = false
                }
            }
        }

        recycler_forecasts.run {
            layoutManager =
                LinearLayoutManager(this@WeatherActivity, RecyclerView.HORIZONTAL, false)
            addItemDecoration(ForecastItemDecoration(this@WeatherActivity))
            adapter = forecastAdapter
        }

        image_input.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean(LocationActivity.EXTRA_IS_FROM_WEATHER, true)
            launchActivity(LocationActivity::class.java, bundle)
        }
    }
}