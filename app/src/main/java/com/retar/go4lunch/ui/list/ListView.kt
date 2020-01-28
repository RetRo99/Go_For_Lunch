package com.retar.go4lunch.ui.list

import com.retar.go4lunch.repository.restaurant.restaurant.model.model.RestaurantEntity

interface ListView {

    fun loadData(data: List<RestaurantEntity>, firstItem: Int)

}