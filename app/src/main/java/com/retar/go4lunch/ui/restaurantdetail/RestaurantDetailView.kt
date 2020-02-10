package com.retar.go4lunch.ui.restaurantdetail

import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import com.retar.go4lunch.base.model.User

interface RestaurantDetailView {

    fun showData(data: UiRestaurantDetailItem)
    fun setFab(picked: Boolean)
    fun showUsers(users: List<User>)
}