package com.retar.go4lunch.repository.users

import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UsersRepositoryModule {

    @Provides
    @Singleton
    fun provideUsersRepository(fireStoreManager: FireStoreManager): UsersRepository {
        return UsersRepositoryImpl(
            fireStoreManager
        )
    }

}
