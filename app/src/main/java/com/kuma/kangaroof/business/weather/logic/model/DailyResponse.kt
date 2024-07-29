package com.xds.mvvmdemo.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * # ******************************************************************
 * # ClassName:      DailyResponse
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/19 15:31
 * # ******************************************************************
 */
data class DailyResponse(val status: String, val result: Result) {
    data class Result(val daily: Daily)

    data class Daily(
        val temperature: List<Temperature>, val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    data class Temperature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeIndex(
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>, val ultraviolet: List<UltraViolet>,
        val dressing: List<Dressing>
    )

    data class LifeDescription(val desc: String)


    data class UltraViolet(val desc: String)//其实从JSON的数据格式来看，下面的两个类均为LifeDescription也可，只是为了验证

    data class Dressing(val desc: String)


}
