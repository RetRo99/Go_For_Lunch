package com.retar.go4lunch.ui

interface MainViewPresenter {

    fun toRestaurantDetail(id: String, title: String)
    fun onCreate()
    fun onDestroy()
    fun onUserLogin()
}