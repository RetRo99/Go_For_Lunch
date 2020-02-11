package com.retar.go4lunch.ui.resturants

import com.retar.go4lunch.manager.contentdata.ContentDataManager
import com.retar.go4lunch.ui.MainViewPresenter
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RestaurantsViewPresenterImpl @Inject constructor(
    private val view: RestaurantsView,
    private val dataManager: ContentDataManager,
    private val parentPresenter: MainViewPresenter
) : RestaurantsViewPresenter {


    private var disposable: Disposable? = null


    private fun observeData() {
        disposable = dataManager.restaurants
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