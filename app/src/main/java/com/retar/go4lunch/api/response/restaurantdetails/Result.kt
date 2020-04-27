package com.retar.go4lunch.api.response.restaurantdetails

data class Result(

    val formatted_address: String,
    val formatted_phone_number: String?,
    val geometry: Geometry,
    val name: String,
    val opening_hours: Opening_hours?,
    val photos: List<Photos>?,
    val place_id: String,
    val vicinity: String,
    val website: String
)
