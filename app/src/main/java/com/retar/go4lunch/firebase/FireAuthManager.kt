package com.retar.go4lunch.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.retar.go4lunch.ui.users.model.User
import io.reactivex.Maybe
import javax.inject.Inject

class FireAuthManager (
    private val auth: FirebaseAuth,
    private val fireStoreManager: FireStoreManager
) {

    fun getCurrentUser(isNewUser: Boolean? = false): Maybe<User> {
        auth.currentUser?.let {
            val user =
                User(it.uid, it.displayName, it.photoUrl.toString(), it.email, it.phoneNumber)
            if (isNewUser!!) fireStoreManager.saveUser(user)
            return Maybe.just(user)
        }
        return Maybe.empty()
    }

    fun logoutUser() {
        auth.signOut()
    }


}