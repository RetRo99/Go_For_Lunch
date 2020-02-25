package com.retar.go4lunch.repository.restaurants

import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.mapper.restaurantentity.RestaurantEntityMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RestaurantsRepositoryModule {

    @Provides
    @Singleton
    fun provideRestaurantsRepository(googlePlacesApi: GooglePlacesApi, firestoreManager: FireStoreManager, mapper: RestaurantEntityMapper): RestaurantsRepository {
        return RestaurantsRepositoryImpl(
            googlePlacesApi,
            firestoreManager,
            mapper
        )
    }

}