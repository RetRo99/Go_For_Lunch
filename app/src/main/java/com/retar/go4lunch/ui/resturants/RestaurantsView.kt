package com.retar.go4lunch.ui.resturants

import androidx.annotation.StringRes
import com.retar.go4lunch.base.model.RestaurantEntity

interface RestaurantsView {

    fun setData(data: List<RestaurantEntity>)
    fun showToast(@StringRes stringResource: Int)

}