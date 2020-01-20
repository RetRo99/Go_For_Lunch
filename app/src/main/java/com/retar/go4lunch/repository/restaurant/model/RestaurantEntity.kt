package com.retar.go4lunch.repository.restaurant.model

import com.google.android.gms.maps.model.LatLng

data class RestaurantEntity(val latLng: LatLng, val name : String, val id:String)