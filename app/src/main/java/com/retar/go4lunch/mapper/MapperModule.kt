package com.retar.go4lunch.mapper

import com.retar.go4lunch.mapper.restaurantdetailui.RestaurantDetailUiModule
import com.retar.go4lunch.mapper.restaurantentity.RestaurantEntityMapperModule
import dagger.Module

@Module(
    includes = [
        RestaurantEntityMapperModule::class,
        RestaurantDetailUiModule::class
    ]
)
class MapperModule