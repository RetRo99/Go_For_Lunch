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
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

class ContentDataManager(
    private val restaurantRepo: RestaurantsRepository,
    private val autocompleteRepository: AutocompleteRepository,
    private val userRepo: UsersRepository,
    private val locationManager: LocationManager,
    private val fireStoreManager: FireStoreManager
) {

    private var compositeDisposable = CompositeDisposable()

    val restaurants: BehaviorSubject<List<RestaurantEntity>> = BehaviorSubject.create()

    private var restaurantsHolder: List<RestaurantEntity>? = null


    init {
        compositeDisposable.add(locationManager.location
            .subscribeBy(
                onNext = {
                    loadNearbyRestaurants(it)
                }
            ))

        compositeDisposable.add(userRepo.getUsers().subscribeBy {
            mapUsersWithRestaurants(it)
        }
        )

        compositeDisposable.add(fireStoreManager.getRatings().subscribeBy {
            mapRatingWithRestaurants(it)
        }
        )

    }

    private fun mapRatingWithRestaurants(ratings: List<RateModel>) {
        restaurantsHolder?.let {
            restaurants.onNext(it.map { restaurant ->
                val rateModel = ratings.find { rateModel ->
                    rateModel.id == restaurant.id
                }

                restaurant.rating = rateModel?.rating
                restaurant

                })
        }
    }

    private fun mapUsersWithRestaurants(users: List<User>) {
        restaurantsHolder?.let {
            restaurants.onNext(it.map { restaurant ->
                val listOfPicked = users.filter { user ->
                    user.pickedRestaurant == restaurant.id
                }
                restaurant.timesPicked = listOfPicked.size
                restaurant
            })
        }
    }

    private fun loadNearbyRestaurants(location: Location) {

        compositeDisposable.add(
            restaurantRepo.getRestaurants(location, DEFAULT_DISTANCE)
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

    }

}
