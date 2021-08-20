package com.xds.mvvmdemo.logic.model

/**
 * # ******************************************************************
 * # ClassName:      Weather
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/19 15:42
 * # ******************************************************************
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)