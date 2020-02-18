package com.retar.go4lunch.manager.contentdata

import com.retar.go4lunch.manager.location.LocationManager
import com.retar.go4lunch.repository.autocomplete.AutocompleteRepository
import com.retar.go4lunch.repository.restaurants.RestaurantsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContentDataManagerModule {

    @Provides
    @Singleton
    fun provideContentDataManager(
        restaurantsRepository: RestaurantsRepository,
        autocompleteRepository: AutocompleteRepository,
        locationManager: LocationManager
    ): ContentDataManager {
        return ContentDataManager(restaurantsRepository, autocompleteRepository, locationManager)
    }
}