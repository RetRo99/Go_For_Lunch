package com.retar.go4lunch.repository.restaurant

import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.api.retrofit.RetrofitModule
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RestaurantsRepositoryModule {

    @Provides
    @Singleton
    fun provideRestaurantsRepository(googlePlacesApi: GooglePlacesApi, firestoreManager: FireStoreManager): RestaurantsRepository {
        return RestaurantsRepositoryImpl(
            googlePlacesApi,
            firestoreManager
        )
    }

}