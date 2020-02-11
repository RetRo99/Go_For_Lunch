package com.retar.go4lunch.api.response.restaurantdetails

import com.google.android.gms.maps.model.LatLng

data class Location(

    val lat: Double,
    val lng: Double
) {
    fun getLatLng(): LatLng {
        return LatLng(this.lat, this.lng)
    }
}