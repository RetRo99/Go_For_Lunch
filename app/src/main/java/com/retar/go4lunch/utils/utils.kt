package com.retar.go4lunch.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun Location.getApiString():String{
    return "${this.latitude},${this.longitude}"
}

fun Location.getLatLng():LatLng{
    return LatLng(this.latitude, this.longitude)
}