package com.retar.go4lunch.api.retrofit

import com.retar.go4lunch.api.response.nearbysearchresponse.NearbySearchResponse
import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApi {

    @GET("nearbysearch/json")
    fun getNearbyRestaurants(@Query("location") location: String, @Query("radius") radius: String): Single<NearbySearchResponse>

    @GET("details/json")
    fun getResturantDetails(@Query("place_id") restaurantId: String): Single<RestaurantDetailResponse>

    @GET("details/json")
    fun getAutocomplete(@Query("input") input: String): Single<RestaurantDetailResponse>

}