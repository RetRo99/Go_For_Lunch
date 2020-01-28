package com.retar.go4lunch.ui.restaurantdetail

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module

@Module
interface RestaurantDetailModule {

    @Binds
    fun bindView(fragment: RestaurantDetailFragment): RestaurantDetailView

    @Binds
    fun bindPresenter(presenterImpl: RestaurantDetailPresenterImpl): RestaurantDetailPresenter

    @Binds
    fun bindFragment(fragment: RestaurantDetailFragment): Fragment

}