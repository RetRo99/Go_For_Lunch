package com.retar.go4lunch.service

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface NotificationServiceModule {
    @ContributesAndroidInjector
    fun fcmService() : NotificationService

}
