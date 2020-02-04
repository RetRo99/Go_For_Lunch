package com.retar.go4lunch.ui

import com.retar.go4lunch.firebase.FirebaseManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val firebaseManager: FirebaseManager

) : MainViewPresenter {

    private var disposable: Disposable? = null


    override fun onCreate() {
        checkForUser()

    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onUserLogin() {
        checkForUser()
    }

    override fun fromMapToRestaurantDetail(id: String, title: String) {
        view.fromMapToResturantDetail(id, title)
    }
    override fun fromListToRestaurantDetail(id: String, title: String) {
        view.fromListToResturantDetail(id, title)
    }

    private fun checkForUser() {
        disposable?.dispose()
        disposable = firebaseManager.getCurrentUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribeBy(
                onSuccess = {
                    view.startApp(it)
                },
                onComplete = {
                    view.loginUser()
                }
            )
    }


}