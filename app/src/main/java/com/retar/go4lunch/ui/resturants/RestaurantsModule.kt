package com.retar.go4lunch.ui.resturants

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module

@Module
internal interface RestaurantsModule {

    @Binds
    fun bindView(fragment: RestaurantsFragment): RestaurantsView

    @Binds
    fun bindPresenter(presenterImpl: RestaurantsViewPresenterImpl): RestaurantsViewPresenter

    @Binds
    fun bindFragment(fragment: RestaurantsFragment): Fragment

}