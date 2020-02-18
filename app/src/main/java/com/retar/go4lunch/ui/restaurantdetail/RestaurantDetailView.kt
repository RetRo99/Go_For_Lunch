package com.retar.go4lunch.ui.restaurantdetail

import android.content.Intent
import androidx.annotation.StringRes
import com.retar.go4lunch.ui.restaurantdetail.model.UiRestaurantDetailItem
import com.retar.go4lunch.base.model.User

interface RestaurantDetailView {

    fun showData(data: UiRestaurantDetailItem)
    fun setFab(picked: Boolean)
    fun showUsers(users: List<User>)
    fun startActivity(intent: Intent)
    fun showToast(@StringRes stringResource: Int)
}