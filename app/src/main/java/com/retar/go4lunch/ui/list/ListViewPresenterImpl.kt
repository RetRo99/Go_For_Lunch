package com.retar.go4lunch.ui.list

import com.retar.go4lunch.repository.restaurant.RestaurantsRepository
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ListViewPresenterImpl @Inject constructor(
    private val view: ListView,
    private val repository: RestaurantsRepository
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
        // TODO not implemented
    }

    override fun onActivityCreated() {
        observeData()
    }
}