package com.retar.go4lunch.ui

import com.retar.go4lunch.ui.users.model.User

interface MainView {

    fun fromMapToResturantDetail(id: String, title: String)
    fun requestLogin()
    fun setDrawerData(user: User)
    fun fromListToResturantDetail(id: String, title: String)
    fun showLogOutDialog()
}