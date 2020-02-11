package com.retar.go4lunch.ui

import android.util.Log
import com.retar.go4lunch.manager.firebase.FireAuthManager
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val authManager: FireAuthManager

) : MainViewPresenter {

    private var disposable: Disposable? = null


    override fun onResume() {
        disposable?.dispose()
        disposable = authManager.getCurrentUser()

            .subscribeBy(
                onError = {
                    //TODO handle error
                    Log.d("čič", it.localizedMessage)
                },
                onSuccess = {
                    view.setDrawerData(it)
                },
                onComplete = {
                    view.requestLogin()
                }
            )
    }

    override fun onDestroy() {
        disposable?.dispose()
    }

    override fun onSignIn(isNewUser: Boolean?) {
        authManager.getCurrentUser(isNewUser)
    }

    override fun onLogoutClicked() {
        view.showLogOutDialog()
    }

    override fun onLogoutConfirmed() {
        authManager.logoutUser()
        view.requestLogin()
    }

    override fun fromMapToRestaurantDetail(id: String, title: String) {
        view.fromMapToResturantDetail(id, title)
    }

    override fun fromListToRestaurantDetail(id: String, title: String) {
        view.fromListToResturantDetail(id, title)
    }

}