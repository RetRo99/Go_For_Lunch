package com.retar.go4lunch.manager.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.retar.go4lunch.manager.firebase.auth.FireAuthManager
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStoreManager(db: FirebaseFirestore): FireStoreManagerImpl {
        return FireStoreManagerImpl(db)
    }

    @Provides
    @Singleton
    fun provideAuthManager(storeManager: FireStoreManagerImpl, auth: FirebaseAuth): FireAuthManager {
        return FireAuthManager(
            auth,
            storeManager
        )
    }

}
