package com.retar.go4lunch.manager.contentdata

import com.retar.go4lunch.repository.restaurant.RestaurantsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContentDataManagerModule {

    @Provides
    @Singleton
    fun provideContentDataManager(restaurantsRepository: RestaurantsRepository):ContentDataManager{
        return ContentDataManager((restaurantsRepository))
    }
}