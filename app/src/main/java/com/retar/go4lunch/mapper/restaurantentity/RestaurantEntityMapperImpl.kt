package com.retar.go4lunch.mapper.restaurantentity

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.base.model.RestaurantEntity

class RestaurantEntityMapperImpl : RestaurantEntityMapper {
    override fun mapToEntity(
        restaurantDetails: List<RestaurantDetailResponse>,
        latLng: LatLng
    ): List<RestaurantEntity> {
        val listRestaurantEntity = mutableListOf<RestaurantEntity>()
        restaurantDetails.forEach {
            val restaurantLatLng = it.result.geometry.location.getLatLng()
            Log.d("čič", 2.toString())

            val distanceToCurrent = getDistance(latLng, restaurantLatLng)
            listRestaurantEntity.add(
                RestaurantEntity(
                    restaurantLatLng,
                    it.result.name,
                    it.result.place_id,
                    it.result.formatted_phone_number,
                    it.result.photos?.map { it.photo_reference },
                    it.result.website,
                    distanceToCurrent,
                    it.result.vicinity,
                    it.result.photos?.get(0)?.photo_reference,
                    getOpenedString(it)
                )
            )
        }
        listRestaurantEntity.sortBy {
            it.distance().toFloat()
        }
        return listRestaurantEntity

    }


    private fun getOpenedString(restaurant: RestaurantDetailResponse): String {
        var openedBoolean: Boolean? = null
        restaurant.result.opening_hours?.open_now?.let {
            openedBoolean = it
        }
        //todo extract strings
        return if (openedBoolean != null) {
            if (openedBoolean as Boolean) "Restaurant is open" else "Restaurant is closed"
        } else {
            "We don't have any information"
        }
    }

    private fun getDistance(startLatLng: LatLng, endLatLng: LatLng): String {
        val locationStart = Location("").apply {
            latitude = startLatLng.latitude
            longitude = startLatLng.longitude
        }

        val locationEnd = Location("").apply {
            latitude = endLatLng.latitude
            longitude = endLatLng.longitude
        }
        return locationStart.distanceTo(locationEnd).toString()
    }


}