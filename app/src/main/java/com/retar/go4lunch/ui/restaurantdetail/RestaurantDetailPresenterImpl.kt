package com.retar.go4lunch.ui.restaurantdetail

import com.retar.go4lunch.repository.restaurantdetail.RestaurantDetailRepository
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RestaurantDetailPresenterImpl @Inject constructor(
    private val view: RestaurantDetailView,
    private val repository: RestaurantDetailRepository
) : RestaurantDetailPresenter {

    private var disposable: Disposable? = null

    override fun onActivityCreated(restaurantId:String) {
        disposable = repository.getRestaurants(restaurantId)
            .subscribeBy (
                onSuccess = {
                    view.showData(it)
                }
            )
    }


    override fun onDestroy() {
        disposable?.dispose()
    }
}