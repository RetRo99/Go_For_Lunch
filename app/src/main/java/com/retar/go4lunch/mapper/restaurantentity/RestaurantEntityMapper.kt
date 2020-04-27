package com.retar.go4lunch.mapper.restaurantentity

import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.base.model.RestaurantEntity

interface RestaurantEntityMapper {

    fun mapToEntity(
        restaurantDetails: List<RestaurantDetailResponse>,
        currentLatLatLng: LatLng
    ): List<RestaurantEntity>

    fun getOpenedText(): Pair<Int, String>
    fun getIsOpened(): Boolean
    fun getOpenedString(): Pair<Int, String>
    fun getRestaurantLatLang(): LatLng
    fun getDistanceToCurrentLocation(currentLatLatLng: LatLng): String
    fun getOpenedUntilInSeconds(formattedOpenedString: String): Int?
    fun getCurrentDay(): String
}
