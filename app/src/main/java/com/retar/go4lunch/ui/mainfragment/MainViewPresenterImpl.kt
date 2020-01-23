package com.retar.go4lunch.ui.mainfragment

import javax.inject.Inject

class MainViewPresenterImpl @Inject constructor(
    private val view: MainView
) : MainViewPresenter {

    override fun onActivityCreated() {
        view.setUpLayout()
    }

    override fun toRestaurantDetail(id: String) {
        view.toRestaurantDetail(id)
    }


}