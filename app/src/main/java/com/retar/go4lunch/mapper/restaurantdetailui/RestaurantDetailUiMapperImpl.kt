package com.retar.go4lunch.mapper.restaurantdetailui

import com.retar.go4lunch.api.response.restaurantdetails.RestaurantDetailResponse
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem

class RestaurantDetailUiMapperImpl : RestaurantDetailUiMapper {


    override fun mapToUi(
        item: RestaurantDetailResponse,
        isPicked: Boolean
    ): UiRestaurantDetailItem {
        return UiRestaurantDetailItem(
            item.result.formatted_phone_number,
            item.result.photos?.map { it.photo_reference },
            item.result.website,
            item.result.name,
            item.result.formatted_address.substringBefore(","),
            isPicked
        )
    }

}