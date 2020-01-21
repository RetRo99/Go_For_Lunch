package com.retar.go4lunch.repository.restaurant

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
class RestaurantsRepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeDetailsRepository(googlePlacesApi: GooglePlacesApi): RestaurantsRepository {
        return RestaurantsRepositoryImpl(googlePlacesApi)
    }

}