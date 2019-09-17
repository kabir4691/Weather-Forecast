package com.kabir.weatherforecast.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kabir.weatherforecast.R
import com.kabir.weatherforecast.data.local.db.DatabaseHelper
import com.kabir.weatherforecast.data.local.prefs.AppPreferences
import com.kabir.weatherforecast.data.remote.ApiInterface
import com.kabir.weatherforecast.utils.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(
    protected val networkHelper: NetworkHelper,
    protected val apiInterface: ApiInterface,
    protected val compositeDisposable: CompositeDisposable,
    protected val appPreferences: AppPreferences,
    protected val databaseHelper: DatabaseHelper
) : ViewModel() {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val errorStringIdLiveData: MutableLiveData<Int> = MutableLiveData()
    val errorStringLiveData: MutableLiveData<String> = MutableLiveData()

    protected fun checkInternetConnectionWithError(): Boolean =
        if (networkHelper.isNetworkConnected()) {
            true
        } else {
            errorStringIdLiveData.postValue(R.string.network_connection_error)
            false
        }

    protected fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()
}