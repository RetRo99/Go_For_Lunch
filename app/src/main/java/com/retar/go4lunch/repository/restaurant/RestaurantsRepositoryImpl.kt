package com.retar.go4lunch.repository.restaurant

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.retar.go4lunch.api.ApiClient
import com.retar.go4lunch.api.response.nearbysearchresponse.NearbySearchResponse
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import com.retar.go4lunch.utils.getApiString
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.Single

class RestaurantsRepositoryImpl: RestaurantsRepository {

    override fun getRestaurants(location: Location, distance:String): Single<List<RestaurantEntity>> {
        return ApiClient.getGoogleMapsRestaurants.getNearbyRestaurants(location.getApiString(), distance)
            .map {
                mapToRestaurantEntity(
                    it, location.getLatLng()
                )
            }
    }

    private fun mapToRestaurantEntity(nearbySearchResponse: NearbySearchResponse, latLng: LatLng): List<RestaurantEntity>{
        val listRestaurantEntity = mutableListOf<RestaurantEntity>()
        nearbySearchResponse.results.forEach {
            val restaurantLatLng = it.geometry.location.getLatLng()

            val distanceToCurrent = getDistance(latLng, restaurantLatLng)
            listRestaurantEntity.add(RestaurantEntity(restaurantLatLng, it.name, it.place_id, distanceToCurrent))
        }
        listRestaurantEntity.sortBy {
            it.distance.toFloat()
        }
        return listRestaurantEntity
    }

    private fun getDistance(startLatLng: LatLng, endLatLng: LatLng):String{
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