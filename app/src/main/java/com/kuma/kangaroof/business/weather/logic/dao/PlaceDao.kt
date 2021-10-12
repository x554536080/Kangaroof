package com.kuma.kangaroof.business.weather.logic.dao

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.google.gson.Gson
import com.kuma.kangaroof.KangaroofApp
import com.xds.mvvmdemo.logic.model.Place

/**
 * # ******************************************************************
 * # ClassName:      PlaceDao
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/21 15:36
 * # ******************************************************************
 */
object PlaceDao {
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }

    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
            KangaroofApp.context.getSharedPreferences("sunny_weather", MODE_PRIVATE)
}