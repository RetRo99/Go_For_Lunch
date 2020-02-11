package com.retar.go4lunch.ui.resturants

import com.retar.go4lunch.repository.restaurant.RestaurantsRepository
import com.retar.go4lunch.repository.restaurantdetail.RestaurantDetailRepository
import com.retar.go4lunch.ui.MainViewPresenter
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RestaurantsViewPresenterImpl @Inject constructor(
    private val view: RestaurantsView,
    private val restaurantsRepository: RestaurantsRepository,
    private val parentPresenter: MainViewPresenter,
    private val restaurantDetailRepository: RestaurantDetailRepository
) : RestaurantsViewPresenter {


    private var disposable: Disposable? = null


    private fun observeData() {
        disposable = restaurantsRepository.restaurants
            .subscribeBy(
                onNext = {
                    view.setData(it, firstPosition)
                },

                onError = {
                    ///todo handle error
                }
            )
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onListItemClick(id: String, title: String, firstItem: Int) {
        firstPosition = firstItem
        parentPresenter.fromListToRestaurantDetail(id, title)
    }

    override fun onActivityCreated() {
        observeData()
    }

    companion object {
        private var firstPosition = 0
    }
}