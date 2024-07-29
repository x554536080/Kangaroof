package com.xds.mvvmdemo.logic

import androidx.lifecycle.liveData
import com.kuma.kangaroof.business.weather.logic.dao.PlaceDao
import com.xds.mvvmdemo.logic.model.Location
import com.xds.mvvmdemo.logic.model.Place
import com.xds.mvvmdemo.logic.model.Weather
import com.xds.mvvmdemo.logic.network.MVVMDemoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import retrofit2.await
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 * # ******************************************************************
 * # ClassName:      Repository
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/8 9:15
 * # ******************************************************************
 */
object Repository {

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {
//                val deferredRealTime = async { MVVMDemoNetwork.weatherService.getRealtimeWeather(lng,lat) }
                val deferredDaily = async { MVVMDemoNetwork.getDailyWeather(lng, lat) }
                val realtimeResponse = MVVMDemoNetwork.weatherService.getRealtimeWeather(lng,lat).await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather = Weather(
                        realtimeResponse.result.realtime,
                        dailyResponse.result.daily
                    )
                    Result.success(weather)
                } else {
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}" +
                                    "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Result.failure<Weather>(e)
        }
        emit(result)
    }

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = MVVMDemoNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

//        val places = mutableListOf<Place>()
//        places.add(Place("南京市", Location("118.2200", "31.1400"), "江苏省南京市"))
//        val resultDemo = Result.success(places)
//        emit(resultDemo)
        emit(result)
    }

//不知道为什么 这个fire函数用不了，还没返回到上面就直接触发observe了
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
}