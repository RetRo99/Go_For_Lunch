package com.retar.go4lunch.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFireStoreManager(db: FirebaseFirestore): FireStoreManager {
        return FireStoreManager(db)
    }

    @Provides
    @Singleton
    fun provideAuthManager(storeManager: FireStoreManager, auth: FirebaseAuth): FireAuthManager {
        return FireAuthManager(auth, storeManager)
    }


}