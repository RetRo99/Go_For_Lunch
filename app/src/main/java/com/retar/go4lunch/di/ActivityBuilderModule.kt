package com.retar.go4lunch.di

import com.retar.go4lunch.MainActivity
import com.retar.go4lunch.UiModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [UiModule::class])
internal interface ActivityBuilderModule {

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

}