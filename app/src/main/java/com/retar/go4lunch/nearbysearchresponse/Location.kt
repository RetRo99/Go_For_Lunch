package com.retar.go4lunch.nearbysearchresponse

import com.google.android.gms.maps.model.LatLng

data class Location (

	val lat : Double,
	val lng : Double
){
	fun getLatLng():LatLng{
		return LatLng(lat, lng)

	}
}