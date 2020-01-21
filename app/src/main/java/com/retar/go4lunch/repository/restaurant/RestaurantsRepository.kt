package com.retar.go4lunch.repository.restaurant

import android.location.Location
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface RestaurantsRepository {

    val list: BehaviorSubject<List<RestaurantEntity>>


    fun getRestaurants(location: Location, distance: String): Single<List<RestaurantEntity>>
}