package com.retar.go4lunch.manager.contentdata

import android.location.Location
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.manager.location.LocationManager
import com.retar.go4lunch.repository.autocomplete.AutocompleteRepository
import com.retar.go4lunch.repository.restaurants.RestaurantsRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

class ContentDataManager(
    private val restaurantRepo: RestaurantsRepository,
    private val autocompleteRepository: AutocompleteRepository,
    private val locationManager: LocationManager
) {

    private var compositeDisposable = CompositeDisposable()

    val restaurants: BehaviorSubject<List<RestaurantEntity>> = BehaviorSubject.create()

    private lateinit var restaurantsHolder: List<RestaurantEntity>


    init {

        compositeDisposable.add(locationManager.location
            .subscribeBy(
                onNext = {
                    loadNearbyRestaurants(it)
                }
            ))
    }


    private fun loadNearbyRestaurants(location: Location) {

        compositeDisposable.add(restaurantRepo.getRestaurants(location, DEFAULT_DISTANCE)
            .subscribeBy(
                onSuccess = {
                    restaurantsHolder = it
                    restaurants.onNext(it)
                },
                onError = {
                    restaurants.onError(it)
                }
            ))
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun searchAutoComplete(searchParam: String, uniqueId: String): Single<List<RestaurantEntity>> {
        return if (searchParam.isEmpty()) {
            Single.just(restaurants.value)

        } else
            autocompleteRepository.getAutocompleteResult(
                searchParam,
                locationManager.location.value!!,
                DEFAULT_DISTANCE,
                uniqueId
            )

    }

    companion object {

        private const val DEFAULT_DISTANCE = "1500"

    }

}