package com.retar.go4lunch.repository.restaurantdetail

import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManagerImpl
import com.retar.go4lunch.mapper.restaurantdetailui.RestaurantDetailUiMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RestaurantDetailRepositoryModule {


    @Provides
    @Singleton
    fun provideRestaurantDetailsRepository(
        googlePlacesApi: GooglePlacesApi,
        fireStoreManager: FireStoreManagerImpl,
        mapper: RestaurantDetailUiMapper
    ): RestaurantDetailRepository {
        return RestaurantDetailRepositoryImpl(
            googlePlacesApi,
            fireStoreManager,
            mapper
        )
    }

}
