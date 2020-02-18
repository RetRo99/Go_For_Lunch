package com.retar.go4lunch.ui.restaurantdetail

import android.util.Log
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.repository.restaurantdetail.RestaurantDetailRepository
import com.retar.go4lunch.repository.users.UsersRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class RestaurantDetailPresenterImpl @Inject constructor(
    private val view: RestaurantDetailView,
    private val repository: RestaurantDetailRepository,
    private val usersRepository: UsersRepository

) : RestaurantDetailPresenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var restaurantId: String

    override fun onActivityCreated(restaurantId: String) {

        this.restaurantId = restaurantId
        compositeDisposable.add(repository.getRestaurant(this.restaurantId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.showData(it)
                    view.setFab(it.isPicked)
                }
            ))

        compositeDisposable.add(
            usersRepository.getUsers()
                .map {
                    it.filter { user ->
                        user.pickedRestaurant == this.restaurantId
                    }
                }
                .subscribeBy(
                    onNext = {
                        Log.d("čič", "test")
                        view.showUsers(it)

                    },
                    onError = {
                        //todo handle error
                    }

                )
        )
    }


    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun onFabClick() {
        compositeDisposable.add(repository.onRestaurantPicked(restaurantId)
            .subscribeBy(
                onSuccess = {
                    when (it) {
                        FireStoreManager.CURRENT_NOT_PICKED -> view.setFab(false)
                        FireStoreManager.CURRENT_PICKED -> view.setFab(true)
                    }
                }
            ))

    }
}