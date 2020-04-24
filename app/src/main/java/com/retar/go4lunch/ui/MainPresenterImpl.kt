package com.retar.go4lunch.ui

import com.retar.go4lunch.manager.contentdata.ContentDataManager
import com.retar.go4lunch.manager.firebase.auth.FireAuthManager
import com.retar.go4lunch.manager.location.LocationManager
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val authManager: FireAuthManager,
    private val dataManagerModule: ContentDataManager,
    private val locationManager: LocationManager


) : MainViewPresenter {
    override fun requestLocation() {
        locationManager.updateLocation()
    }

    private var disposable: Disposable? = null


    override fun onResume() {
        disposable?.dispose()
        disposable = authManager.getCurrentUser()

            .subscribeBy(
                onError = {
                    view.requestLogin()
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
        dataManagerModule.onDestroy()
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
