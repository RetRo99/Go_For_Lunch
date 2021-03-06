package com.retar.go4lunch.manager.firebase.auth

import com.google.firebase.auth.FirebaseAuth
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManagerImpl
import io.reactivex.Maybe

class FireAuthManager(
    private val auth: FirebaseAuth,
    private val fireStoreManager: FireStoreManagerImpl
) {


    fun getCurrentUser(isNewUser: Boolean? = false): Maybe<User> {
        auth.currentUser?.let {
            val user =
                User(it.uid, it.displayName, it.photoUrl.toString(), it.email, it.phoneNumber)
            if (isNewUser!!) fireStoreManager.saveUser(user)
            fireStoreManager.getFireStoreUser(user.id)
            return Maybe.just(user)
        }
        return Maybe.empty()
    }

    fun logoutUser() {
        auth.signOut()
    }


}
