package com.retar.go4lunch.api.response.restaurantdetails

data class Opening_hours(

    val open_now: Boolean,
    val periods: List<Periods>,
    val weekday_text: List<String>
)