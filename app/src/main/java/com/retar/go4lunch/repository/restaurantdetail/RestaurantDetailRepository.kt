package com.retar.go4lunch.repository.restaurantdetail

import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import io.reactivex.Single

interface RestaurantDetailRepository {

    fun getRestaurant(id: String): Single<UiRestaurantDetailItem>
    fun onRestaurantPicked(id: String, title: String): Single<String>

}
