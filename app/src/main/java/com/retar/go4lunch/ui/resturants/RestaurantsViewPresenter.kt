package com.retar.go4lunch.ui.resturants

interface RestaurantsViewPresenter {

    fun onDestroy()
    fun onListItemClick(id: String, title: String, firstItem: Int)
    fun onActivityCreated()
    fun onSearchChanged(text: CharSequence?)


}