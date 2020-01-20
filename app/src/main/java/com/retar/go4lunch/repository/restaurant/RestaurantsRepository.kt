package com.retar.go4lunch.repository.restaurant

import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import io.reactivex.Single

interface RestaurantsRepository {

    fun getRestaurants(locationString:String, distance:String): Single<List<RestaurantEntity>>
}