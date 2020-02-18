package com.retar.go4lunch.base.model

import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.R

data class RestaurantEntity(
    val latLng: LatLng,
    val name: String,
    val id: String,
    val phoneNumber: String?,
    val photoReferences: List<String>?,
    val webPage: String?,
    private val distance: String,
    private val address: String,
    val photoUrl: String?,
    val isOpenedNow: String,
    @DrawableRes var icon: Int = R.drawable.ic_restaurant_marker_orange
) {

    fun distance(): String {
        return distance.substringBefore(".")
    }

    fun address(): String {
        return address.substringBefore(",")
    }
}
