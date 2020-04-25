package com.retar.go4lunch.di

import com.retar.go4lunch.api.retrofit.RetrofitModule
import com.retar.go4lunch.manager.notificationManager.NotificationHelperModule
import com.retar.go4lunch.manager.ManagerModule
import com.retar.go4lunch.mapper.MapperModule
import com.retar.go4lunch.repository.RepositoryModule
import com.retar.go4lunch.service.NotificationServiceModule
import dagger.Module

@Module(
    includes = [
        ManagerModule::class,
        RepositoryModule::class,
        RetrofitModule::class,
        MapperModule::class,
        NotificationServiceModule::class

    ]
)class MainModule
