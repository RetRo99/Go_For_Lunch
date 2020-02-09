package com.retar.go4lunch.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.retar.go4lunch.ui.users.model.User
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class FireStoreManager(
    db: FirebaseFirestore
) {

    private val userRef: CollectionReference
    private lateinit var currentUser: User

    init {
        userRef = db.collection(USERS_COLLECTION)
        getUsers()
    }


    val users: BehaviorSubject<List<User>> = BehaviorSubject.create()


    fun saveUser(user: User) {
        userRef.document(user.id).set(user)
    }

    fun getFireStoreUser(id: String) {
        userRef.document(id).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            currentUser = documentSnapshot?.toObject(User::class.java)!!
        }
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


    fun onRestaurantPicked(id: String): Single<String> {
        return if (isCurrentPicked(id)) {
            updatePickedRestaurant("")
            Single.just(CURRENT_NOT_PICKED)
        } else {
            updatePickedRestaurant(id)
            Single.just(CURRENT_PICKED)
        }
    }


    private fun updatePickedRestaurant(id: String) {
        userRef.document(currentUser.id).update(PICKED_RESTAURANT, id)

    }

    fun checkIfPicked(id: String): Boolean {
        return isCurrentPicked(id)
    }

    private fun isCurrentPicked(id: String): Boolean {
        return (id == currentUser.pickedRestaurant)
    }

    companion object {
        private const val USERS_COLLECTION = "users"
        private const val PICKED_RESTAURANT = "pickedRestaurant"
        const val CURRENT_PICKED = "changed"
        const val CURRENT_NOT_PICKED = "deleted"
    }
}
