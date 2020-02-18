package com.retar.go4lunch.di

import dagger.Module

@Module(includes = [dagger.android.support.AndroidSupportInjectionModule::class, UiInjectorModule::class, AppAndroidBindModule::class, MainModule::class])
class AppModule