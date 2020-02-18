package com.retar.go4lunch.ui.users

import androidx.annotation.StringRes
import com.retar.go4lunch.base.model.User

interface UsersView {

    fun setData(data:List<User>)
    fun showToast(@StringRes stringResource: Int)

}