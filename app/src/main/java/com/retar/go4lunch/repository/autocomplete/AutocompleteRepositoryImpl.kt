package com.retar.go4lunch.repository.autocomplete

import android.location.Location
import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.mapper.restaurantentity.RestaurantEntityMapper
import com.retar.go4lunch.utils.getApiString
import com.retar.go4lunch.utils.getLatLng
import io.reactivex.Single

class AutocompleteRepositoryImpl(
    private val googlePlacesApi: GooglePlacesApi,
    private val firestoreManager: FireStoreManager,
    private val restaurantDetailMapper: RestaurantEntityMapper
) : AutocompleteRepository {

    override fun getAutocompleteResult(
        searchParam: String,
        location: Location,
        radius: String,
        uniqueId: String
    ): Single<List<RestaurantEntity>> {
        return googlePlacesApi.getAutocomplete(
            searchParam,
            location.getApiString(),
            radius,
            uniqueId
        )
            .flattenAsObservable { it.predictions }
            .filter {
                "food" in it.types
            }
            .flatMapSingle {
                googlePlacesApi.getResturantDetails(it.place_id)
            }
            .toList()
            .map {
                restaurantDetailMapper.mapToEntity(it, location.getLatLng())

            }
            .flatMap {
                firestoreManager.mapWithVisitedRestaurantsAndRating(it)
            }
    }

}
