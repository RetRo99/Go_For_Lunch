package com.retar.go4lunch.repository.restaurant

import android.location.Location
import com.retar.go4lunch.base.model.RestaurantEntity
import io.reactivex.Single

interface RestaurantsRepository {

    fun getRestaurants(
        location: Location,
        distance: String
    ): Single<List<RestaurantEntity>>

}