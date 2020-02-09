package com.retar.go4lunch.repository.users

import com.retar.go4lunch.firebase.FireStoreManager
import com.retar.go4lunch.ui.users.model.User
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val fireStoreManager: FireStoreManager

) : UsersRepository {

    override fun getUsers(): BehaviorSubject<List<User>> {
        return fireStoreManager.users
    }

}