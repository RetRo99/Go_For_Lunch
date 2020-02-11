package com.retar.go4lunch.manager.contentdata

import android.location.Location
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.repository.restaurant.RestaurantsRepository
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

class ContentDataManager(
    private val restaurantRepo: RestaurantsRepository
) {

    private var disposable: Disposable? = null

    val restaurants: BehaviorSubject<List<RestaurantEntity>> = BehaviorSubject.create()


    fun loadNearbyRestaurants(location: Location, distance: String, resetData: Boolean) {

        if (restaurants.value == null || resetData) {
            disposable = restaurantRepo.getRestaurants(location, distance)
                .subscribeBy(
                    //todo handle error
                    onSuccess = {
                        restaurants.onNext(it)
                    },
                    onError = {
                        restaurants.onError(it)
                    }
                )
        }

    }

}
