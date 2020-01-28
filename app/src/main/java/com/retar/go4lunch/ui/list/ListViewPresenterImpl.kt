package com.retar.go4lunch.ui.list

import com.retar.go4lunch.repository.restaurant.RestaurantsRepository
import com.retar.go4lunch.ui.MainViewPresenter
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ListViewPresenterImpl @Inject constructor(
    private val view: ListView,
    private val repository: RestaurantsRepository,
    private val parentPresenter: MainViewPresenter
) : ListViewPresenter {


    private var disposable: Disposable? = null


    private fun observeData() {
        disposable = repository.list
            .subscribeBy(
                onNext = {
                    view.loadData(it)
                },

                onError = {
                    ///todo handle error
                }
            )
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onListItemClick(id: String) {
        parentPresenter.toRestaurantDetail(id)
    }

    override fun onActivityCreated() {
        observeData()
    }
}