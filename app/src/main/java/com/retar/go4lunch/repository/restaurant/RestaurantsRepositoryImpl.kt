package com.retar.go4lunch.repository.restaurant

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.api.response.nearbysearchresponse.NearbySearchResponse
import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.firebase.FireStoreManager
import com.retar.go4lunch.utils.getApiString
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val googlePlacesApi: GooglePlacesApi,
    private val firestoreManager: FireStoreManager
) :
    RestaurantsRepository {

    override val restaurants: BehaviorSubject<List<RestaurantEntity>> = BehaviorSubject.create()

    private var disposable: Disposable? = null

    override fun getRestaurants(location: Location, distance: String, resetData: Boolean) {

        if (restaurants.value == null || resetData) {
            disposable = googlePlacesApi.getNearbyRestaurants(
                location.getApiString(),
                distance
            )
                .map {
                    mapToIds(it)
                }
                .flatMapObservable {
                    Observable.fromIterable(it)
                }
                .flatMapSingle {
                    googlePlacesApi.getResturantDetails(it)
                }
                .toList()
                .map {
                    mapDetailsToEntity(it, location.getLatLng())
                }
                .flatMap {
                    firestoreManager.mapWithVisitedRestaurants(it)
                }
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

    private fun mapDetailsToEntity(
        restaurantDetails: List<RestaurantDetailResponse>,
        latLng: LatLng
    ): List<RestaurantEntity> {
        val listRestaurantEntity = mutableListOf<RestaurantEntity>()
        restaurantDetails.forEach {
            val restaurantLatLng = it.result.geometry.location.getLatLng()

            val distanceToCurrent = getDistance(latLng, restaurantLatLng)
            listRestaurantEntity.add(
                RestaurantEntity(
                    restaurantLatLng,
                    it.result.name,
                    it.result.place_id,
                    it.result.formatted_phone_number,
                    it.result.photos.map { it.photo_reference },
                    it.result.website,
                    distanceToCurrent,
                    it.result.vicinity,
                    it.result.photos[0].photo_reference,
                    getOpenedString(it)
                )
            )
        }
        listRestaurantEntity.sortBy {
            it.distance().toFloat()
        }
        return listRestaurantEntity
    }


    private fun mapToIds(
        nearbySearchResponse: NearbySearchResponse
    ): List<String> {
        val restaurantIds = mutableListOf<String>()
        nearbySearchResponse.results.forEach {
            restaurantIds.add(it.place_id)
        }
        return restaurantIds
    }

    private fun getOpenedString(restaurant: RestaurantDetailResponse): String {
        var openedBoolean: Boolean? = null
        restaurant.result.opening_hours?.open_now?.let {
            openedBoolean = it
        }
        //todo extract strings
        return if (openedBoolean != null) {
            if (openedBoolean as Boolean) "Restaurant is open" else "Restaurant is closed"
        } else {
            "We don't have any information"
        }
    }

    private fun getDistance(startLatLng: LatLng, endLatLng: LatLng): String {
        val locationStart = Location("").apply {
            latitude = startLatLng.latitude
            longitude = startLatLng.longitude
        }

        val locationEnd = Location("").apply {
            latitude = endLatLng.latitude
            longitude = endLatLng.longitude
        }
        return locationStart.distanceTo(locationEnd).toString()
    }
}