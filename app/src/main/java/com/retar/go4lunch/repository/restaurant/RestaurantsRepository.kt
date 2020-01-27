package com.retar.go4lunch.repository.restaurant

import android.location.Location
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import io.reactivex.subjects.BehaviorSubject

interface RestaurantsRepository {

    val list: BehaviorSubject<List<RestaurantEntity>>


    fun getRestaurants(location: Location, distance: String, resetData: Boolean)
}