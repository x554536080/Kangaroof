package com.xds.mvvmdemo.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.xds.mvvmdemo.logic.Repository
import com.xds.mvvmdemo.logic.model.Location

/**
 * # ******************************************************************
 * # ClassName:      WeatherViewModel
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/21 13:02
 * # ******************************************************************
 */
class WeatherViewModel : ViewModel() {
    private val locationLiveData = MutableLiveData<Location>()

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.refreshWeather(location.lng, location.lat)
    }

    var locationLng = ""

    var locationLat = ""

    var placeName = ""
}