package com.retar.go4lunch.repository.users

import com.retar.go4lunch.base.model.User
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface UsersRepository {

    fun getUsers(): Observable<List<User>>

}