package com.retar.go4lunch.ui.holderfragment

import javax.inject.Inject

class HolderViewPresenterImpl @Inject constructor(
    private val view: HolderView
) : HolderViewPresenter {

    override fun onActivityCreated() {
        view.setUpLayout()
    }

}