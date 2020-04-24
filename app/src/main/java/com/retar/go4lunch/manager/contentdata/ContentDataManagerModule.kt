package com.retar.go4lunch.manager.contentdata

import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.manager.location.LocationManager
import com.retar.go4lunch.repository.autocomplete.AutocompleteRepository
import com.retar.go4lunch.repository.restaurants.RestaurantsRepository
import com.retar.go4lunch.repository.users.UsersRepository
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
        usersRepository: UsersRepository,
        locationManager: LocationManager,
        fireStoreManager: FireStoreManager
    ): ContentDataManager {
        return ContentDataManager(
            restaurantsRepository,
            autocompleteRepository,
            usersRepository,
            locationManager,
            fireStoreManager
        )
    }
}
