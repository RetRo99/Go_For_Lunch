package com.retar.go4lunch

import com.retar.go4lunch.ui.map.MapFragment
import com.retar.go4lunch.ui.map.MapModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface UiModule {

    @ContributesAndroidInjector(modules = [MapModule::class])
    fun contributeMapFragmentInjector(): MapFragment

}