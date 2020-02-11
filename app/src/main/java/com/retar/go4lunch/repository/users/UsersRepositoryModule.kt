package com.retar.go4lunch.repository.users

import com.retar.go4lunch.manager.firebase.FireStoreManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
//    (
//    includes = [
//        RetrofitModule::class
//    ]
//)
class UsersRepositoryModule {


    @Provides
    @Singleton
    fun provideUsersRepository(fireStoreManager: FireStoreManager): UsersRepository {
        return UsersRepositoryImpl(
            fireStoreManager
        )
    }

}
