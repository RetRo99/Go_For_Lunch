package com.retar.go4lunch.repository.users

import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import io.reactivex.Observable

class UsersRepositoryImpl (
    private val fireStoreManager: FireStoreManager

) : UsersRepository {

    override fun getUsers(): Observable<List<User>> {
        return fireStoreManager.getUsers()
            .map {
                it.sortedBy {user ->
                    user.pickedRestaurantTitle.isNullOrBlank()
                }
            }

    }
    }


