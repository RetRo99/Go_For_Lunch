package com.retar.go4lunch.repository.restaurants

import android.location.Location
import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.base.model.RateModel
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.mapper.restaurantentity.RestaurantEntityMapper
import com.retar.go4lunch.utils.getApiString
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

class RestaurantsRepositoryImpl(
    private val googlePlacesApi: GooglePlacesApi,
    private val fireStoreManager: FireStoreManager,
    private val restaurantDetailMapper: RestaurantEntityMapper

) :
    RestaurantsRepository {


    override fun getRestaurants(
        location: Location,
        distance: String
    ): Observable<List<RestaurantEntity>> {

        return googlePlacesApi.getNearbyRestaurants(
            location.getApiString(),
            distance
        )
            .map { it.results.map { it.place_id } }
            .flattenAsObservable { it }
            .flatMapSingle {
                googlePlacesApi.getResturantDetails(it)
            }
            .toList()
            .map {
                restaurantDetailMapper.mapToEntity(it, location.getLatLng())
            }
            .flatMapObservable {
                fireStoreManager.mapWithVisitedRestaurantsAndRating(it)
            }
    }

}
