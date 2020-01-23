package com.retar.go4lunch.di

import com.retar.go4lunch.ui.list.ListFragment
import com.retar.go4lunch.ui.list.ListModule
import com.retar.go4lunch.ui.mainfragment.MainFragment
import com.retar.go4lunch.ui.mainfragment.MainModule
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

    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainFragmentInjector(): MainFragment

}