package com.xds.mvvmdemo.logic.model

import com.google.gson.annotations.SerializedName

/**
 * # ******************************************************************
 * # ClassName:      PlaceResponse
 * # Description:    TODO
 * # Author:         hmxiong
 * # Version:        Ver 1.0
 * # Create Date     2021/5/6 13:36
 * # ******************************************************************
 */

data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

data class Location(val lng: String, val lat: String)

