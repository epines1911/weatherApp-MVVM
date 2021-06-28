package com.xiaowang.weatherapp.service

import com.xiaowang.weatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

//    http://api.openweathermap.org/data/2.5/weather?q=beijing&APPID=9b2b130f98fcf250cf5dcd557ce35ec5

    @GET("data/2.5/weather?&units=metric&APPID=9b2b130f98fcf250cf5dcd557ce35ec5")
    fun getData(@Query("q") cityName: String): Single<WeatherModel>
}