package com.retar.go4lunch.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerApplication

@Module
interface AppAndroidBindModule {

    @Binds
    fun bindContext(app: BaseApplication): Context

}