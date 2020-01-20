package com.retar.go4lunch.di

import com.retar.go4lunch.ui.map.MapFragment
import com.retar.go4lunch.ui.map.MapModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Module
internal interface UiModule {

    @ContributesAndroidInjector(modules = [MapModule::class])
    fun contributeMapFragmentInjector(): MapFragment

}