package com.retar.go4lunch.repository

import com.retar.go4lunch.repository.autocomplete.AutocompleteRepositoryModule
import com.retar.go4lunch.repository.restaurants.RestaurantsRepositoryModule
import com.retar.go4lunch.repository.restaurantdetail.RestaurantDetailRepositoryModule
import com.retar.go4lunch.repository.users.UsersRepositoryModule
import dagger.Module

@Module(
    includes = [
        RestaurantsRepositoryModule::class,
        RestaurantDetailRepositoryModule::class,
        UsersRepositoryModule::class,
        AutocompleteRepositoryModule::class
    ]
)
class RepositoryModule