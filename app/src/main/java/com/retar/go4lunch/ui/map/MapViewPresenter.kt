package com.retar.go4lunch.ui.map

interface MapViewPresenter {

    fun onActivityCreated()
    fun onMapReady()
    fun onDestroy()
    fun onMarkerClicked(id: String, title: String)
    fun onSearchChanged(text: CharSequence?)

}
