package com.retar.go4lunch.api.response.restaurantdetails

data class Reviews(

    val author_name: String,
    val author_url: String,
    val language: String,
    val profile_photo_url: String,
    val rating: Int,
    val relative_time_description: String,
    val text: String,
    val time: Int
)