package com.retar.go4lunch.repository.autocomplete

import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManagerImpl
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
        firestoreManager: FireStoreManagerImpl,
        mapper: RestaurantEntityMapper
    ): AutocompleteRepository {
        return AutocompleteRepositoryImpl(api, firestoreManager, mapper)
    }

}



