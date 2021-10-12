package com.xds.mvvmdemo.logic.model

import com.google.gson.annotations.SerializedName

/**
 * # ******************************************************************
 * # ClassName:      RealtimeResponse
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/19 15:23
 * # ******************************************************************
 */
data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        val skycon: String,
        val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)

}