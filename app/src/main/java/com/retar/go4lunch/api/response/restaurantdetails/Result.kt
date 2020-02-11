package com.retar.go4lunch.api.response.restaurantdetails

data class Result(

    val address_components: List<Address_components>,
    val adr_address: String,
    val formatted_address: String,
    val formatted_phone_number: String,
    val geometry: Geometry,
    val icon: String,
    val id: String,
    val international_phone_number: String,
    val name: String,
    val opening_hours: Opening_hours?,
    val photos: List<Photos>,
    val place_id: String,
    val plus_code: Plus_code,
    val rating: Double,
    val reference: String,
    val reviews: List<Reviews>,
    val scope: String,
    val types: List<String>,
    val url: String,
    val user_ratings_total: Int,
    val utc_offset: Int,
    val vicinity: String,
    val website: String
)