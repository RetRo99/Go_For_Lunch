package com.retar.go4lunch.repository.autocomplete

import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.mapper.restaurantentity.RestaurantEntityMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AutocompleteRepositoryModule {

    @Provides
    @Singleton
    fun provideAutocompleteRepository(
        api: GooglePlacesApi,
        firestoreManager: FireStoreManager,
        mapper: RestaurantEntityMapper
    ): AutocompleteRepository {
        return AutocompleteRepositoryImpl(api, firestoreManager, mapper)
    }

}



