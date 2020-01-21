package com.retar.go4lunch.ui.list

import com.retar.go4lunch.repository.restaurant.model.RestaurantEntity

interface ListView {

    fun loadData(data: List<RestaurantEntity>)

}