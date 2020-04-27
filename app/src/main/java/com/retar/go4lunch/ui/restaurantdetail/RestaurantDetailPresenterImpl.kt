package com.retar.go4lunch.ui.restaurantdetail

import android.content.Intent
import android.net.Uri
import com.retar.go4lunch.R
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManagerImpl
import com.retar.go4lunch.repository.restaurantdetail.RestaurantDetailRepository
import com.retar.go4lunch.repository.users.UsersRepository
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
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

    lateinit var data: UiRestaurantDetailItem

    override fun onActivityCreated(restaurantId: String) {

        this.restaurantId = restaurantId
        compositeDisposable.add(repository.getRestaurant(this.restaurantId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    data = it
                    view.showData(it)
                    view.setFab(it.isPicked)
                },
                onError = {
                    view.showToast(R.string.error_no_data)
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
                        view.showUsers(it)

                    },
                    onError = {
                        view.showToast(R.string.error_no_user_data)
                    }

                )
        )
    }

    override fun onCallClicked() {
        if (!data.phoneNumber.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${data.phoneNumber}")
            view.openActivity(intent)
        } else {
            view.showToast(R.string.restaurant_detail_no_number)

        }
    }

    override fun onWebSiteClicked() {
        if (!data.webPage.isNullOrEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(data.webPage)
            view.openActivity(intent)
        } else {
            view.showToast(R.string.restaurant_detail_not_available)
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    override fun onFabClick() {
        compositeDisposable.add(repository.onRestaurantPicked(restaurantId, data.name)
            .subscribeBy(
                onSuccess = {
                    when (it) {
                        FireStoreManagerImpl.CURRENT_NOT_PICKED -> view.setFab(false)
                        FireStoreManagerImpl.CURRENT_PICKED -> view.setFab(true)
                    }
                }
            ))

    }
}
