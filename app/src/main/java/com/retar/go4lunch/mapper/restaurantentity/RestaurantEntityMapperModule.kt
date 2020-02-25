package com.retar.go4lunch.mapper.restaurantentity

import dagger.Module
import dagger.Provides

@Module
class RestaurantEntityMapperModule {

    @Provides
    fun providesRestaurantDetailMapper(): RestaurantEntityMapper {
        return RestaurantEntityMapperImpl()
    }

}