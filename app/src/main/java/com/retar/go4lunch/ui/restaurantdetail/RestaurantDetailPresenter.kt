package com.retar.go4lunch.ui.restaurantdetail

interface RestaurantDetailPresenter {

    fun onActivityCreated(restaurantId: String)
    fun onDestroy()
    fun onFabClick()
    fun onCallClicked()
    fun onWebSiteClicked()
}