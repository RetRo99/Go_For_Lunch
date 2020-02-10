package com.retar.go4lunch.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.retar.go4lunch.R
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.firebase.model.FireStoreRestaurant
import de.aaronoe.rxfirestore.getObservable
import de.aaronoe.rxfirestore.getSingle
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

class FireStoreManager(
    db: FirebaseFirestore
) {


    private val userRef: CollectionReference
    private val visitedRef: CollectionReference
    private var currentUser: User? = null

    init {
        userRef = db.collection(USERS_COLLECTION)
        visitedRef = db.collection(VISITED_COLLECTION)
    }

    fun saveUser(user: User) {
        userRef.document(user.id).set(user)
    }

    fun getFireStoreUser(id: String) {
        userRef.document(id).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            currentUser = documentSnapshot?.toObject(User::class.java)!!
        }
    }


     fun getUsers(): Observable<List<User>> {
        return userRef.getObservable()
    }


    private fun mapToVisited(
        fireStoreRestaurants: List<FireStoreRestaurant>,
        restaurantsEntity: List<RestaurantEntity>
    ): List<RestaurantEntity> {

        val visitedIds = fireStoreRestaurants.map {
            it.id
        }

        return restaurantsEntity.map {
            if (it.id in visitedIds) it.icon = R.drawable.ic_restaurant_marker_green
            it
        }

    }

    fun mapWithVisitedRestaurants(restaurantsEntity: List<RestaurantEntity>): Single<List<RestaurantEntity>> {
        return visitedRef.getSingle<FireStoreRestaurant>()
            .map { firestoreRestaurants ->
                mapToVisited(firestoreRestaurants, restaurantsEntity)

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
        userRef.document(currentUser!!.id).update(PICKED_RESTAURANT, id)
    }

    fun checkIfPicked(id: String): Boolean {
        return isCurrentPicked(id)
    }

    private fun isCurrentPicked(id: String): Boolean {
        return (id == currentUser?.pickedRestaurant)
    }


    companion object {
        private const val USERS_COLLECTION = "users"
        private const val VISITED_COLLECTION = "visitedRestaurants"

        private const val PICKED_RESTAURANT = "pickedRestaurant"
        const val CURRENT_PICKED = "changed"
        const val CURRENT_NOT_PICKED = "deleted"
    }
}
