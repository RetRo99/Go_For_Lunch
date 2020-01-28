package com.retar.go4lunch.ui.map

import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.ui.map.model.UiMarkerModel

interface MapView {

    fun addMarkers(markers: List<UiMarkerModel>)
    fun getLastLocation(isFromFab: Boolean)
    fun getMapAsync()
    fun animateToLocation(latLng: LatLng)
    fun moveToLocation(latLng: LatLng)

}