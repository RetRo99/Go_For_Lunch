package com.retar.go4lunch.repository.restaurantdetail

import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.api.retrofit.RetrofitModule
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RestaurantDetailRepositoryModule {


    @Provides
    @Singleton
    fun provideRestaurantDetailsRepository(
        googlePlacesApi: GooglePlacesApi,
        fireStoreManager: FireStoreManager
    ): RestaurantDetailRepository {
        return RestaurantDetailRepositoryImpl(
            googlePlacesApi,
            fireStoreManager
        )
    }

}
