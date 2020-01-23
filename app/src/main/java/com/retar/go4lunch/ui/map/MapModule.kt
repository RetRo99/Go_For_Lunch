package com.retar.go4lunch.ui.map

import androidx.fragment.app.Fragment
import com.retar.go4lunch.ui.mainfragment.MainViewPresenter
import com.retar.go4lunch.ui.mainfragment.MainViewPresenterImpl
import dagger.Binds
import dagger.Module

@Module
internal interface MapModule {

    @Binds
    fun bindView(fragment: MapFragment): MapView

    @Binds
    fun bindPresenter(presenterImpl: MapViewPresenterImpl): MapViewPresenter

    @Binds
    fun bindFragment(fragment: MapFragment): Fragment

}