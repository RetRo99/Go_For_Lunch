package com.retar.go4lunch.repository.restaurant

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RestaurantsRepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeDetailsRepository(): RestaurantsRepository {
        return RestaurantsRepositoryImpl()
    }

}