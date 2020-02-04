package com.retar.go4lunch.ui

import com.retar.go4lunch.ui.list.ListFragment
import com.retar.go4lunch.ui.list.ListModule
import com.retar.go4lunch.ui.map.MapFragment
import com.retar.go4lunch.ui.map.MapModule
import com.retar.go4lunch.ui.restaurantdetail.RestaurantDetailFragment
import com.retar.go4lunch.ui.restaurantdetail.RestaurantDetailModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface UiModule {

    @ContributesAndroidInjector(modules = [MapModule::class])
    fun contributeMapFragmentInjector(): MapFragment

    @ContributesAndroidInjector(modules = [ListModule::class])
    fun contributeListFragmentInjector(): ListFragment

    @ContributesAndroidInjector(modules = [RestaurantDetailModule::class])
    fun contributeRestaurantDetailFragmentInjector(): RestaurantDetailFragment

}