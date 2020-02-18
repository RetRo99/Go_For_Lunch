package com.retar.go4lunch.manager

import com.retar.go4lunch.manager.contentdata.ContentDataManagerModule
import com.retar.go4lunch.manager.firebase.FirebaseModule
import com.retar.go4lunch.manager.location.LocationManagerModule
import dagger.Module


@Module(
    includes = [
        FirebaseModule::class,
        ContentDataManagerModule::class,
        LocationManagerModule::class
    ]
)
class ManagerModule