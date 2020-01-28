package com.retar.go4lunch.repository.restaurantdetail

import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class RestaurantDetailRepositoryImpl @Inject constructor(private val googlePlacesApi: GooglePlacesApi) :
    RestaurantDetailRepository {


    override fun getRestaurants(id: String): Single<UiRestaurantDetailItem> {
        return googlePlacesApi.getResturantDetails(id)
            .map {
                mapToUi(it)
            }
            .observeOn(AndroidSchedulers.mainThread())


    }

    private fun mapToUi(it: RestaurantDetailResponse): UiRestaurantDetailItem {
        return UiRestaurantDetailItem(
            it.result.formatted_phone_number,
            it.result.photos.map { it.photo_reference },
            it.result.website,
            it.result.name,
            it.result.formatted_address.substringBefore(","))
    }


}