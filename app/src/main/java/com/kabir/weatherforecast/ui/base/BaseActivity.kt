package com.kabir.weatherforecast.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kabir.weatherforecast.App
import com.kabir.weatherforecast.di.component.ActivityComponent
import com.kabir.weatherforecast.di.component.DaggerActivityComponent
import com.kabir.weatherforecast.di.module.ActivityModule
import com.kabir.weatherforecast.utils.showToast
import javax.inject.Inject

abstract class BaseActivity<VM: BaseViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        setupObservers()
        setupView()
    }

    private fun buildActivityComponent() =
        DaggerActivityComponent.builder()
            .applicationComponent((application as App).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()

    protected open fun setupObservers() {
        viewModel.errorStringIdLiveData.observe(this, Observer {
            showToast(getString(it))
        })
        viewModel.errorStringLiveData.observe(this, Observer {
            showToast(it)
        })
    }

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun setupView()
}