package com.retar.go4lunch.ui

import com.retar.go4lunch.ui.resturants.RestaurantsFragment
import com.retar.go4lunch.ui.resturants.RestaurantsModule
import com.retar.go4lunch.ui.map.MapFragment
import com.retar.go4lunch.ui.map.MapModule
import com.retar.go4lunch.ui.restaurantdetail.RestaurantDetailFragment
import com.retar.go4lunch.ui.restaurantdetail.RestaurantDetailModule
import com.retar.go4lunch.ui.users.UsersFragment
import com.retar.go4lunch.ui.users.UsersModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface UiModule {

    @ContributesAndroidInjector(modules = [MapModule::class])
    fun contributeMapFragmentInjector(): MapFragment

    @ContributesAndroidInjector(modules = [RestaurantsModule::class])
    fun contributeListFragmentInjector(): RestaurantsFragment

    @ContributesAndroidInjector(modules = [RestaurantDetailModule::class])
    fun contributeRestaurantDetailFragmentInjector(): RestaurantDetailFragment

    @ContributesAndroidInjector(modules = [UsersModule::class])
    fun contributeUsersFragmentFragmentInjector(): UsersFragment

}