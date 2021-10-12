package com.kuma.kangaroof.business.weather.logic.network

import com.kuma.kangaroof.KangaroofApp
import com.xds.mvvmdemo.logic.model.DailyResponse
import com.xds.mvvmdemo.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * # ******************************************************************
 * # ClassName:      WeatherService
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/19 15:45
 * # ******************************************************************
 */
interface WeatherService {
    @GET("v2.5/${KangaroofApp.WEATHER_TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Call<RealtimeResponse>

    @GET("v2.5/${KangaroofApp.WEATHER_TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String):
            Call<DailyResponse>
}