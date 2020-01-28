package com.retar.go4lunch.di

import com.retar.go4lunch.repository.restaurant.RestaurantsRepositoryModule
import com.retar.go4lunch.repository.restaurantdetail.RestaurantDetailRepositoryModule
import dagger.Module

@Module(
    includes = [
        RestaurantsRepositoryModule::class,
        RestaurantDetailRepositoryModule::class
    ]
)
class RepositoryModule