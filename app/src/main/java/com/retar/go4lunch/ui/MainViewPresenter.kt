package com.retar.go4lunch.ui

interface MainViewPresenter {

    fun fromMapToRestaurantDetail(id: String, title: String)
    fun fromListToRestaurantDetail(id: String, title: String)
    fun onResume()
    fun onDestroy()
    fun onSignIn(isNewUser: Boolean?)
    fun onLogoutClicked()
    fun onLogoutConfirmed()
    fun requestLocation()
    fun onNavigateToDetail(id: String)
    fun onRestaurantRated(rate: Double)
    fun onYourLunchClicked()
}
