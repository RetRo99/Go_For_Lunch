package com.retar.go4lunch.ui.map

import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.ui.map.model.UiMarkerModel

interface MapView {

    fun addMarker(marker: UiMarkerModel)
    fun getMapAsync()
    fun animateToLocation(latLng: LatLng)
    fun moveToLocation(latLng: LatLng)
    fun setMarkerClickListener()
    fun setDarkTheme()
    fun deleteAllMarkers()

}