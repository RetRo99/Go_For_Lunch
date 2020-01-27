package com.retar.go4lunch.di

import com.retar.go4lunch.ui.holderfragment.HolderFragment
import com.retar.go4lunch.ui.holderfragment.HolderModule
import com.retar.go4lunch.ui.list.ListFragment
import com.retar.go4lunch.ui.list.ListModule
import com.retar.go4lunch.ui.map.MapFragment
import com.retar.go4lunch.ui.map.MapModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal interface UiModule {

    @ContributesAndroidInjector(modules = [MapModule::class])
    fun contributeMapFragmentInjector(): MapFragment

    @ContributesAndroidInjector(modules = [ListModule::class])
    fun contributeListFragmentInjector(): ListFragment

    @ContributesAndroidInjector(modules = [HolderModule::class])
    fun contributeMainFragmentInjector(): HolderFragment

}