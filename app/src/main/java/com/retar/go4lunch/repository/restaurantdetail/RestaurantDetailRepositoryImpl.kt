package com.retar.go4lunch.repository.restaurantdetail

import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class RestaurantDetailRepositoryImpl (
    private val googlePlacesApi: GooglePlacesApi,
    private val fireStoreManager: FireStoreManager
) :
    RestaurantDetailRepository {


    override fun getRestaurant(id: String): Single<UiRestaurantDetailItem> {
        return googlePlacesApi.getResturantDetails(id)
            .map {
                mapToUi(it, fireStoreManager.checkIfPicked(it.result.place_id))
            }
            .observeOn(AndroidSchedulers.mainThread())


    }

    override fun onRestaurantPicked(id: String): Single<String> {
        return fireStoreManager.onRestaurantPicked(id)
    }

    private fun mapToUi(it: RestaurantDetailResponse, isPicked: Boolean): UiRestaurantDetailItem {
        return UiRestaurantDetailItem(
            it.result.formatted_phone_number,
            it.result.photos?.map { it.photo_reference },
            it.result.website,
            it.result.name,
            it.result.formatted_address.substringBefore(","),
            isPicked
        )
    }


}