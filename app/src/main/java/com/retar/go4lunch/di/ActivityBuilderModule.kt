package com.retar.go4lunch.di

import com.retar.go4lunch.ui.MainActivity
import com.retar.go4lunch.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [RepositoryModule::class])
internal interface ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainActivity(): MainActivity

}