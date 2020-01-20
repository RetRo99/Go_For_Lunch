package com.retar.go4lunch.di

import com.retar.go4lunch.repository.restaurant.RestaurantsRepositoryModule
import dagger.Module

@Module(
    includes = [
        RestaurantsRepositoryModule::class
    ]
)
class RepositoryModule