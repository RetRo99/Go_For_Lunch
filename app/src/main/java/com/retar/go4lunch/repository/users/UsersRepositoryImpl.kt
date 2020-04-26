package com.retar.go4lunch.repository.users

import android.util.Log
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import io.reactivex.Observable
import io.reactivex.Single

class UsersRepositoryImpl (
    private val fireStoreManager: FireStoreManager

) : UsersRepository {

    var users = listOf<User>()


    override fun getUsers(): Observable<List<User>> {
        return fireStoreManager.getUsers()
            .map {
                val sortedList = it.sortedBy { user ->
                    user.pickedRestaurantTitle.isNullOrBlank()
                }
                users = sortedList
                sortedList
            }

    }

    override fun getFilteredUsers(param: String): Single<List<User>> {
        return if (param.isNotEmpty()) {
            Single.just(users.filter {
                it.name?.contains(param, true) ?: false ||  it.pickedRestaurantTitle?.contains(param, true) ?: false
            })
        } else {
            Single.just(users)
        }
    }
}


