package com.retar.go4lunch.ui.resturants

import com.retar.go4lunch.base.model.RestaurantEntity

interface RestaurantsView {

    fun setData(data: List<RestaurantEntity>, firstItem: Int)

}