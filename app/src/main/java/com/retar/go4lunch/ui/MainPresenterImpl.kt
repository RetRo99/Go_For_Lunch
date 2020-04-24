package com.retar.go4lunch.ui

import com.retar.go4lunch.base.model.RateModel
import com.retar.go4lunch.manager.contentdata.ContentDataManager
import com.retar.go4lunch.manager.firebase.auth.FireAuthManager
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.manager.location.LocationManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import kotlin.math.roundToInt

class MainPresenterImpl @Inject constructor(
    private val view: MainView,
    private val authManager: FireAuthManager,
    private val dataManagerModule: ContentDataManager,
    private val locationManager: LocationManager,
    private val fireStoreManager: FireStoreManager


) : MainViewPresenter {

    private lateinit var currentId: String

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun requestLocation() {
        locationManager.updateLocation()
    }

    override fun onNavigateToDetail(id: String) {
        currentId = id
    }

    override fun onRestaurantRated(rate: Double) {
        fireStoreManager.getCurrentRating()
            .flattenAsObservable { it }
            .filter {
                it.id == currentId
            }
            .toList()
            .subscribeBy {
                if (it.isEmpty()) {
                    fireStoreManager.addRating(
                        RateModel(
                            rate.toFloat(),
                            1,
                            rate,
                            currentId
                        )
                    )
                } else {
                    fireStoreManager.updateRating(getNewRate(it[0], rate))

                }


            }.addTo(compositeDisposable)
    }

    private fun getNewRate(rateModel: RateModel, rate: Double): RateModel {
        val newTimesRated = rateModel.timesRated + 1
        val newRatingsCombined = rateModel.ratingsCombined +  rate
        val newRate = newRatingsCombined/newTimesRated
        return RateModel(rating = newRate.toFloat(), timesRated = newTimesRated, ratingsCombined = newRatingsCombined, id = currentId)
    }


    override fun onResume() {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
        authManager.getCurrentUser()

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
            ).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
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
