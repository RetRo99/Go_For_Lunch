package com.retar.go4lunch.repository.restaurantdetail

import com.retar.go4lunch.api.retrofit.GooglePlacesApi
import com.retar.go4lunch.api.retrofit.RetrofitModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(
    includes = [
        RetrofitModule::class
    ]
)
class RestaurantDetailRepositoryModule {


    @Provides
    @Singleton
    fun provideRestaurantDetailsRepository(googlePlacesApi: GooglePlacesApi): RestaurantDetailRepository {
        return RestaurantDetailRepositoryImpl(
            googlePlacesApi
        )
    }

}
