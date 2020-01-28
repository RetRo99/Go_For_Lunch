package com.retar.go4lunch.api.response.restaurantdetails

data class RestaurantDetailResponse(

    val html_attributions: List<String>,
    val result: Result,
    val status: String
)