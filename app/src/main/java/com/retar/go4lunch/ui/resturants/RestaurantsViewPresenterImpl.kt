package com.retar.go4lunch.ui.resturants

import com.retar.go4lunch.manager.contentdata.ContentDataManager
import com.retar.go4lunch.ui.MainViewPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RestaurantsViewPresenterImpl @Inject constructor(
    private val view: RestaurantsView,
    private val dataManager: ContentDataManager,
    private val parentPresenter: MainViewPresenter
) : RestaurantsViewPresenter {


    private var disposable: Disposable? = null


    private fun observeData() {
        disposable?.dispose()
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

    override fun onSearchChanged(text: CharSequence?) {
        disposable?.dispose()
        disposable = Observable.just(text.toString())
            .delay(3, TimeUnit.SECONDS)
            .subscribeBy(
                onNext = {
                    view.toast(it)
                }
            )
    }


    companion object {
        private var firstPosition = 0
    }
}