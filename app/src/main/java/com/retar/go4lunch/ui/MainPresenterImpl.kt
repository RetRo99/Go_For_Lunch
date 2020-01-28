package com.retar.go4lunch.ui

import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView

) : MainViewPresenter {

    override fun toRestaurantDetail(id: String) {
        view.fromHolderToResturantDetail(id)
    }


}