package com.retar.go4lunch.mapper.restaurantdetailui

import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem

interface RestaurantDetailUiMapper{

    fun mapToUi(item: RestaurantDetailResponse, isPicked: Boolean): UiRestaurantDetailItem
}