package com.retar.go4lunch.di

import com.retar.go4lunch.ui.MainActivity
import com.retar.go4lunch.ui.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [MainModule::class])
internal interface ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributeMainActivity(): MainActivity

}