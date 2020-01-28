package com.retar.go4lunch.ui.restaurantdetail

import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem

interface RestaurantDetailView {

    fun showData(data: UiRestaurantDetailItem)
}