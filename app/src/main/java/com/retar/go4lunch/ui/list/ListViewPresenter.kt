package com.retar.go4lunch.ui.list

interface ListViewPresenter {

    fun onDestroy()
    fun onListItemClick(id: String, title: String, firstItem: Int)
    fun onActivityCreated()

}