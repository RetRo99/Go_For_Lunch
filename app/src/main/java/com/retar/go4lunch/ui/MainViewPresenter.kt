package com.retar.go4lunch.ui

interface MainViewPresenter {

    fun fromMapToRestaurantDetail(id: String, title: String)
    fun fromListToRestaurantDetail(id: String, title: String)
    fun onCreate()
    fun onDestroy()
    fun onUserLogin()
}