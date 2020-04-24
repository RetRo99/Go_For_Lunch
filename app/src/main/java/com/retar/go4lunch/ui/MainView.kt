package com.retar.go4lunch.ui

import androidx.annotation.StringRes
import com.retar.go4lunch.base.model.User

interface MainView {

    fun fromMapToResturantDetail(id: String, title: String)
    fun requestLogin()
    fun setDrawerData(user: User)
    fun fromListToResturantDetail(id: String, title: String)
    fun showLogOutDialog()
    fun showToast(@StringRes stringRes: Int)
    fun fromDrawerToDetail(id: String, title: String)
}
