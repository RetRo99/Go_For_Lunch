package com.retar.go4lunch.ui.resturants

import android.util.Log
import com.retar.go4lunch.R
import com.retar.go4lunch.manager.contentdata.ContentDataManager
import com.retar.go4lunch.ui.MainViewPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RestaurantsViewPresenterImpl @Inject constructor(
    private val view: RestaurantsView,
    private val dataManager: ContentDataManager,
    private val parentPresenter: MainViewPresenter
) : RestaurantsViewPresenter {

    private val searchText: BehaviorSubject<String> = BehaviorSubject.create()

    private var disposable: Disposable? = null

    private var searchDisposable: Disposable? = null

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onListItemClick(id: String, title: String) {
        uniqueId = UUID.randomUUID().toString()
        parentPresenter.fromListToRestaurantDetail(id, title)
    }

    override fun onActivityCreated() {
        disposable?.dispose()
        disposable = dataManager.restaurants
            .subscribeBy(
                onNext = {
                    view.setData(it)
                },

                onError = {
                    view.showToast(R.string.error_no_data)
                }
            )

        searchDisposable = searchText
            .debounce(750, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeBy(
                onNext = {
                    dataManager.searchAutoComplete(it, uniqueId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            onSuccess = { restaurants ->
                                view.setData(restaurants)
                            }
                        )
                },
                onError = {
                    view.setData(listOf())
                }
            )
    }

    override fun onSearchChanged(text: CharSequence?) {
        searchText.onNext(text.toString())
    }

    companion object{
        private var uniqueId = UUID.randomUUID().toString()
    }

}