package com.kuma.kangaroof.logic.network

import com.kuma.kangaroof.KangaroofApp
import com.xds.mvvmdemo.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * # ******************************************************************
 * # ClassName:      PlaceService
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/6 13:46
 * # ******************************************************************
 */
interface PlaceService {

    @GET("v2.5/places?token=${KangaroofApp.WEATHER_TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}