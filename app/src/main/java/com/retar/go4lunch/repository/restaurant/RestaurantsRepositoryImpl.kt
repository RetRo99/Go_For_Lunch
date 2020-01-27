package com.retar.go4lunch.repository.restaurant

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.api.response.nearbysearchresponse.NearbySearchResponse
import com.retar.go4lunch.api.response.nearbysearchresponse.Results
import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import com.retar.go4lunch.utils.getApiString
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(private val googlePlacesApi: GooglePlacesApi) :
    RestaurantsRepository {

    override val list: BehaviorSubject<List<RestaurantEntity>> = BehaviorSubject.create()

    private var disposable: Disposable? = null

    override fun getRestaurants(location: Location, distance: String, resetData: Boolean) {

        if (list.value == null || resetData) {
            disposable = googlePlacesApi.getNearbyRestaurants(
                location.getApiString(),
                distance
            )
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    mapToRestaurantEntity(
                        it, location.getLatLng()
                    )
                }
                .subscribeBy(
                    onSuccess = {
                        list.onNext(it)
                        disposable?.dispose()
                    }
                )

        }


    }

    private fun mapToRestaurantEntity(
        nearbySearchResponse: NearbySearchResponse,
        latLng: LatLng
    ): List<RestaurantEntity> {
        val listRestaurantEntity = mutableListOf<RestaurantEntity>()
        nearbySearchResponse.results.forEach {
            val restaurantLatLng = it.geometry.location.getLatLng()

            val distanceToCurrent = getDistance(latLng, restaurantLatLng)
            listRestaurantEntity.add(
                RestaurantEntity(
                    restaurantLatLng,
                    it.name,
                    it.place_id,
                    distanceToCurrent,
                    it.vicinity,
                    it.photos?.get(0)?.photo_reference,
                    getOpenedString(it)
                )
            )
        }
        listRestaurantEntity.sortBy {
            it.distance().toFloat()
        }
        return listRestaurantEntity
    }

    private fun getOpenedString(restaurant: Results): String {
        var openedBoolean: Boolean? = null
        restaurant.opening_hours?.open_now?.let {
            openedBoolean = it
        }
        //todo extract strings
        return if (openedBoolean != null) {
            if (openedBoolean as Boolean) "Restaurant is opened" else "Restaurant is closed"
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