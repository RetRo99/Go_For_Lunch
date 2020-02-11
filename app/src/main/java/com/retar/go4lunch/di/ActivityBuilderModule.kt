package com.retar.go4lunch.di

import com.retar.go4lunch.manager.firebase.FirebaseModule
import com.retar.go4lunch.repository.RepositoryModule
import com.retar.go4lunch.ui.MainActivity
import com.retar.go4lunch.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [RepositoryModule::class, FirebaseModule::class])
internal interface ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainActivity(): MainActivity

}