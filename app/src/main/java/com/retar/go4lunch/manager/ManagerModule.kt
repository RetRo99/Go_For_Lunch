package com.retar.go4lunch.manager

import com.retar.go4lunch.manager.contentdata.ContentDataManagerModule
import com.retar.go4lunch.manager.firebase.FirebaseModule
import com.retar.go4lunch.repository.RepositoryModule
import dagger.Module


@Module(
    includes = [
        FirebaseModule::class,
        ContentDataManagerModule::class
    ]
)
class ManagerModule