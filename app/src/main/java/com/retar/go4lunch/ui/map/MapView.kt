package com.retar.go4lunch.ui.map

import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.ui.map.model.UiMarkerModel

interface MapView {

    fun addMarkers(markers: List<UiMarkerModel>)
    fun getLastLocation()
    fun getMapAsync()
    fun zoomToLocation(latLng: LatLng)

}