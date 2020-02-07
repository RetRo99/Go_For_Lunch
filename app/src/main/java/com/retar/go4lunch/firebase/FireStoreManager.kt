package com.retar.go4lunch.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.retar.go4lunch.firebase.model.User
import javax.inject.Inject

class FireStoreManager @Inject constructor(
    db: FirebaseFirestore

) {


    private val userRef = db.collection(USERS_COLLECTION)

    fun saveUser(user: User) {
        userRef.document(user.id).set(user)
    }


    companion object {
        private const val USERS_COLLECTION = "users"
    }
}
