package com.retar.go4lunch.repository.restaurants

import android.location.Location
import com.retar.go4lunch.base.model.RestaurantEntity
import io.reactivex.Observable
import io.reactivex.Single

interface RestaurantsRepository {

    fun getRestaurants(
        location: Location,
        distance: String
    ): Observable<List<RestaurantEntity>>

}
