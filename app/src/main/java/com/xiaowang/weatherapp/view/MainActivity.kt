package com.xiaowang.weatherapp.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.xiaowang.weatherapp.R
import com.xiaowang.weatherapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var GET:SharedPreferences
    private lateinit var SET:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var cityName = GET.getString("cityName", "Beijing").toString()
//        edt_search.setText(cityName)

        viewModel.refreshData(cityName)

        getLiveData()

        swipeRefreshLayout.setOnRefreshListener {
            tv_temperature.visibility = View.GONE
            tv_error.visibility = View.GONE
            progressBar_loading.visibility = View.GONE

            var cityName2 = GET.getString("cityName",cityName).toString()
            edt_search.setText(cityName2)
            viewModel.refreshData(cityName2)
            swipeRefreshLayout.isRefreshing = false
        }

        img_search.setOnClickListener {
            val cityNameForSearch = edt_search.text.toString()
            SET.putString("cityName", cityNameForSearch)
            SET.apply()
            viewModel.refreshData(cityNameForSearch)
            getLiveData()
        }

    }

    private fun getLiveData() {
        viewModel.weatherData.observe(this, Observer { data ->
            data.let {
                tv_temperature.text = data.main.temp.toString()
                tv_temperature.visibility = View.VISIBLE
                tv_countryCode.text = data.sys.country
                tv_cityName.text = data.name

                Glide.with(this).load("http://openweathermap.org/img/wn/"
                        + data.weather[0].icon
                        + "@2x.png")
                    .into(img_weather)
            }
        })

        viewModel.weatherLoad.observe(this, Observer { load ->
            load.let {
                if(it){
                    progressBar_loading.visibility = View.VISIBLE
                    tv_error.visibility = View.GONE
                    tv_temperature.visibility = View.GONE
                } else {
                    progressBar_loading.visibility = View.GONE
                }
            }
        })

        viewModel.weatherError.observe(this, Observer { error ->
            error.let {
                if(it){
                    progressBar_loading.visibility = View.GONE
                    tv_error.visibility = View.VISIBLE
                    tv_temperature.visibility = View.GONE
                } else {
                    tv_error.visibility = View.GONE
                }
            }
        })
    }
}