package com.retar.go4lunch.base.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.R

data class RestaurantEntity(
    val latLng: LatLng,
    val name: String,
    val id: String,
    val phoneNumber: String?,
    val photoReferences: List<String>?,
    val webPage: String?,
    val distance: String,
    private val address: String,
    val photoUrl: String?,
    val openedText: Pair<Int, String>,
    @DrawableRes var icon: Int = R.drawable.ic_restaurant_marker_orange,
     var timesPicked: Int = 0,
     var rating: Float? = 0F
) {

    fun address(): String {
        return address.substringBefore(",")
    }

    fun peopleVisiting():String {
        return "($timesPicked)"
    }
}
