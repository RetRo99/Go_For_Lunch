package com.retar.go4lunch.api

import com.retar.go4lunch.api.response.nearbysearchresponse.NearbySearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {

    @GET("nearbysearch/json")
    fun getNearbyRestaurants(@Query("location") location:String, @Query("radius") radius:String): Single<NearbySearchResponse>

}