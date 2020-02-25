package com.retar.go4lunch.mapper.restaurantentity

import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.base.model.RestaurantEntity

interface RestaurantEntityMapper {

    fun mapToEntity(
        restaurantDetails: List<RestaurantDetailResponse>,
        latLng: LatLng
    ): List<RestaurantEntity>
}