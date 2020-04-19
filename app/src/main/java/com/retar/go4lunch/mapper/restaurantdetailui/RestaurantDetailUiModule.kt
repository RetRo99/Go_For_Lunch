package com.retar.go4lunch.mapper.restaurantdetailui

import dagger.Module
import dagger.Provides

@Module
class RestaurantDetailUiModule {

    @Provides
    fun providesRestaurantDetailMapper(): RestaurantDetailUiMapper {
        return RestaurantDetailUiMapperImpl()
    }
}
