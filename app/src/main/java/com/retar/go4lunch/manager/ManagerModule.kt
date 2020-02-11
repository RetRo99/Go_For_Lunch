package com.retar.go4lunch.manager

import com.retar.go4lunch.manager.firebase.FirebaseModule
import dagger.Module


@Module(
    includes = [
        FirebaseModule::class
    ]
)
class ManagerModule