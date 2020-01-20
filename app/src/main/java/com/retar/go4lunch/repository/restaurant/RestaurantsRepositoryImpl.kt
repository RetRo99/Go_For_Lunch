package com.retar.go4lunch.repository.restaurant

import com.retar.go4lunch.api.ApiClient
import com.retar.go4lunch.api.response.nearbysearchresponse.NearbySearchResponse
import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity
import io.reactivex.Single

class RestaurantsRepositoryImpl: RestaurantsRepository {

    override fun getRestaurants(locationString:String, distance:String): Single<List<RestaurantEntity>> {
        return ApiClient.getGoogleMapsRestaurants.getNearbyRestaurants(locationString, distance)
            .map {
                mapToRestaurantEntity(
                    it
                )
            }
    }

    private fun mapToRestaurantEntity(nearbySearchResponse: NearbySearchResponse): List<RestaurantEntity>{
        val listRestaurantEntity = mutableListOf<RestaurantEntity>()
        nearbySearchResponse.results.forEach {
            listRestaurantEntity.add(RestaurantEntity(it.geometry.location.getLatLng(), it.name, it.place_id))
        }
        return listRestaurantEntity
    }
}