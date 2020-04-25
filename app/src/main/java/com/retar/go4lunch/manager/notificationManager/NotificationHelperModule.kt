package com.retar.go4lunch.manager.notificationManager

import android.content.Context
import com.retar.go4lunch.manager.notificationManager.NotificationHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NotificationHelperModule {

    @Provides
    @Singleton
    fun provideNotificationHelper(context: Context): NotificationHelper {
        return NotificationHelper(
            context
        )
    }
}
