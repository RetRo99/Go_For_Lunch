package com.retar.go4lunch.ui

import com.google.firebase.auth.FirebaseUser

interface MainView {

    fun fromMapToResturantDetail(id: String, title: String)
    fun loginUser()
    fun startApp(user: FirebaseUser)
    fun fromListToResturantDetail(id: String, title: String)

}