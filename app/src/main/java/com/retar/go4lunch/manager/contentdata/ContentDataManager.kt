package com.retar.go4lunch.manager.contentdata

import android.location.Location
import com.retar.go4lunch.base.model.RateModel
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.manager.location.LocationManager
import com.retar.go4lunch.repository.autocomplete.AutocompleteRepository
import com.retar.go4lunch.repository.restaurants.RestaurantsRepository
import com.retar.go4lunch.repository.users.UsersRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ContentDataManager(
    private val restaurantRepo: RestaurantsRepository,
    private val autocompleteRepository: AutocompleteRepository,
    private val locationManager: LocationManager
) {

    private var compositeDisposable = CompositeDisposable()

    val restaurants: BehaviorSubject<List<RestaurantEntity>> = BehaviorSubject.create()

    private var restaurantsHolder: List<RestaurantEntity>? = null

    private var lastLocation: Location? = null


    init {
        addLocationObserver()

        createLocationInterval()
    }

    private fun createLocationInterval() {
        compositeDisposable.add(
            Observable.interval(0, 20, TimeUnit.SECONDS)
                .subscribeBy {
                    locationManager.updateLocation()
                }

        )
    }

    private fun addLocationObserver() {
        compositeDisposable.add(
            locationManager.location
                .subscribeBy(
                    onNext = {
                        handleNewLocation(it)
                    })
        )
    }

    private fun handleNewLocation(location: Location) {

        when {
            lastLocation == null -> {
                loadNearbyRestaurants(location)
                lastLocation = location
            }

            restaurantsHolder == null -> loadNearbyRestaurants(location)

            else -> {
                val distance = lastLocation!!.distanceTo(location)

                if (distance > MAX_DISTANCE_BETWEEN_LOCATION) {
                    loadNearbyRestaurants(location)
                    lastLocation = location

                }


            }

        }
    }

    private fun loadNearbyRestaurants(location: Location) {

        compositeDisposable.add(
            restaurantRepo.getRestaurants(location, DEFAULT_DISTANCE)
                .subscribeBy(
                    onNext = {
                        restaurantsHolder = it
                        restaurants.onNext(it)
                    },
                onError = {
                }
            ))
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun searchAutoComplete(searchParam: String, uniqueId: String): Observable<List<RestaurantEntity>> {
        return if (searchParam.isEmpty()) {
            Observable.fromArray(restaurantsHolder ?: listOf())

        } else {
            autocompleteRepository.getAutocompleteResult(
                searchParam,
                locationManager.location.value!!,
                DEFAULT_DISTANCE,
                uniqueId
            )
        }
    }

    companion object {

        private const val DEFAULT_DISTANCE = "5000"
        private const val MAX_DISTANCE_BETWEEN_LOCATION = 200f

    }

}
