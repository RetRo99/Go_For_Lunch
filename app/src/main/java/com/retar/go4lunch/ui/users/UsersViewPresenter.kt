package com.retar.go4lunch.ui.users

interface UsersViewPresenter {

    fun onActivityCreated()
    fun onDestroy()
    fun onSearchChanged(text: CharSequence?)

}
