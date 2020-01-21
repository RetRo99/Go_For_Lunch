package com.retar.go4lunch.repository.restaurant.model

import com.google.android.gms.maps.model.LatLng

data class RestaurantEntity(
    val latLng: LatLng,
    val name: String,
    val id: String,
    private val distance: String,
    private val address: String,
    val photoUrl: String?,
    val isOpenedNow:String
) {

    fun distance(): String {
        return distance.substringBefore(".")
    }

    fun address(): String {
        return address.substringBefore(",")
    }
}
