package com.retar.go4lunch.ui.map

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface MapModule {

    @Binds
    fun bindView(fragment: MapFragment): MapView

    @Binds
    fun bindPresenter(presenterImpl: MapViewPresenterImpl): MapViewPresenter

    @Binds
    fun bindFragment(fragment: MapFragment): Fragment

}