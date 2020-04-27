package com.retar.go4lunch.repository.users

import com.retar.go4lunch.manager.firebase.firestore.FireStoreManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UsersRepositoryModule {

    @Provides
    @Singleton
    fun provideUsersRepository(fireStoreManager: FireStoreManagerImpl): UsersRepository {
        return UsersRepositoryImpl(
            fireStoreManager
        )
    }

}
