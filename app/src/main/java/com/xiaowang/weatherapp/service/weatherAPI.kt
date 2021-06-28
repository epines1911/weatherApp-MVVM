package com.xiaowang.weatherapp.service

import com.xiaowang.weatherapp.model.weatherModel
import io.reactivex.Single
import retrofit2.http.GET

interface weatherAPI {

//    http://api.openweathermap.org/data/2.5/weather?q=bingol&APPID=9b2b130f98fcf250cf5dcd557ce35ec5

    @GET("data/2.5/weather?q=bingol&APPID=9b2b130f98fcf250cf5dcd557ce35ec5")
    fun getData(): Single<weatherModel>
}