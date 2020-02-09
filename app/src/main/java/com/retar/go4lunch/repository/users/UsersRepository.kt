package com.retar.go4lunch.repository.users

import com.retar.go4lunch.ui.users.model.User
import io.reactivex.subjects.BehaviorSubject

interface UsersRepository {

    fun getUsers(): BehaviorSubject<List<User>>

}