package com.retar.go4lunch.ui.map

import android.location.Location


interface MapViewPresenter {

    fun onActivityCreated()
    fun onMapReady()
    fun onGotLastLocation(location: Location)
    fun zoomToCurrentLocation()

}
