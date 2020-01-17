package com.retar.go4lunch

import com.retar.go4lunch.nearbysearchresponse.NearbySearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantApi {

    @GET("nearbysearch/json")
    fun getNearbySquareRestaurants(@Query("location") location:String, @Query("radius") radius:String): Single<NearbySearchResponse>

}