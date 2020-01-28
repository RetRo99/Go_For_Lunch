package com.retar.go4lunch.repository.restaurantdetail

import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import io.reactivex.Single

interface RestaurantDetailRepository {

    fun getRestaurants(id: String): Single<UiRestaurantDetailItem>


}