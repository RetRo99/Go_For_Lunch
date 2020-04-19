package com.retar.go4lunch.repository.restaurantdetail

import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.mapper.restaurantdetailui.RestaurantDetailUiMapper
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class RestaurantDetailRepositoryImpl (
    private val googlePlacesApi: GooglePlacesApi,
    private val fireStoreManager: FireStoreManager,
    private val mapper: RestaurantDetailUiMapper
) :
    RestaurantDetailRepository {


    override fun getRestaurant(id: String): Single<UiRestaurantDetailItem> {
        return googlePlacesApi.getResturantDetails(id)
            .map {
                mapper.mapToUi(it, fireStoreManager.checkIfPicked(it.result.place_id))
            }
            .observeOn(AndroidSchedulers.mainThread())


    }

    override fun onRestaurantPicked(id: String, title: String): Single<String> {
        return fireStoreManager.onRestaurantPicked(id, title)
    }

}
