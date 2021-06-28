package com.xiaowang.weatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import com.xiaowang.weatherapp.model.weatherModel
import com.xiaowang.weatherapp.service.weatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class mainViewModel {
    private val WeatherAPIService = weatherAPIService()
    private val disposable = CompositeDisposable()

    val weatherData = MutableLiveData<weatherModel>()
    val weatherError = MutableLiveData<Boolean>()
    val weatherLoad = MutableLiveData<Boolean>()

    fun refreshData(){
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        weatherLoad.value = true
        disposable.add(
            WeatherAPIService.getDataService().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<weatherModel>(){
                    override fun onSuccess(t: weatherModel) {
                        weatherData.value = t
                        weatherError.value = false
                        weatherLoad.value = false
                    }

                    override fun onError(e: Throwable) {
                        weatherError.value = true
                        weatherLoad.value = false
                    }
                })
        )

    }
}