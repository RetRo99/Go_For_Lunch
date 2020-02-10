package com.retar.go4lunch.repository.users

import com.retar.go4lunch.firebase.FireStoreManager
import com.retar.go4lunch.base.model.User
import io.reactivex.Observable
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val fireStoreManager: FireStoreManager

) : UsersRepository {

    override fun getUsers(): Observable<List<User>> {
        return fireStoreManager.getUsers()
    }

}