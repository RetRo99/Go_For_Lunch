package com.retar.go4lunch.repository

import com.retar.go4lunch.api.retrofit.RetrofitModule
import com.retar.go4lunch.repository.restaurant.RestaurantsRepositoryModule
import com.retar.go4lunch.repository.restaurantdetail.RestaurantDetailRepositoryModule
import com.retar.go4lunch.repository.users.UsersRepositoryModule
import dagger.Module

@Module(
    includes = [
        RestaurantsRepositoryModule::class,
        RestaurantDetailRepositoryModule::class,
        UsersRepositoryModule::class
    ]
)
class RepositoryModule