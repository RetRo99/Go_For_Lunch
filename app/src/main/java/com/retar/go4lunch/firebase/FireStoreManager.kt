package com.retar.go4lunch.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.retar.go4lunch.ui.users.model.User
import io.reactivex.subjects.BehaviorSubject

class FireStoreManager(
    db: FirebaseFirestore

) {

    private val userRef: CollectionReference

    init {
        userRef = db.collection(USERS_COLLECTION)
        getUsers()
    }


    val users: BehaviorSubject<List<User>> = BehaviorSubject.create()

    fun saveUser(user: User) {
        userRef.document(user.id).set(user)
    }

    private fun getUsers() {
        userRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                users.onError(e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                users.onNext(snapshot.documents.map {
                    it.toObject(User::class.java)!!
                })

            } else {
                users.onNext(listOf())
            }
        }

    }


    companion object {
        private const val USERS_COLLECTION = "users"
    }
}
