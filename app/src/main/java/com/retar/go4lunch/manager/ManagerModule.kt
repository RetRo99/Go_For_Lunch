package com.retar.go4lunch.manager

import com.retar.go4lunch.manager.contentdata.ContentDataManagerModule
import com.retar.go4lunch.manager.firebase.FirebaseModule
import com.retar.go4lunch.manager.location.LocationManagerModule
import com.retar.go4lunch.manager.notificationManager.NotificationHelperModule
import dagger.Module


@Module(
    includes = [
        FirebaseModule::class,
        ContentDataManagerModule::class,
        LocationManagerModule::class,
        NotificationHelperModule::class
    ]
)
class ManagerModule
