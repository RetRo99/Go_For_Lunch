package com.retar.go4lunch.manager.location

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationManagerModule {

    @Provides
    @Singleton
    fun provideReactiveLocationProvider(context:Context): FusedLocationProviderClient {
        return  LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationManager(provider: FusedLocationProviderClient): LocationManager {
        return LocationManager(provider)
    }

}