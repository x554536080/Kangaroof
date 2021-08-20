package com.xds.mvvmdemo.logic.network

import com.kuma.kangaroof.logic.network.PlaceService
import com.kuma.kangaroof.logic.network.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * # ******************************************************************
 * # ClassName:      MVVMDemoNetwork
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/6 18:06
 * # ******************************************************************
 */
object MVVMDemoNetwork {

    private val weatherService = ServiceCreator.create(WeatherService::class.java)
    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng, lat).await()

    suspend fun getRealtimeWeather(lng: String, lat: String) =
        weatherService.getRealtimeWeather(lng, lat).await()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()

                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("respond body is null")
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}