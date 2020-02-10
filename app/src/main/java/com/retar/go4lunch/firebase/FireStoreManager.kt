package com.retar.go4lunch.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.retar.go4lunch.R
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.firebase.model.FireStoreRestaurant
import de.aaronoe.rxfirestore.getSingle
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject

class FireStoreManager(
    db: FirebaseFirestore
) {

    val users: BehaviorSubject<List<User>> = BehaviorSubject.create()
    lateinit var visitedRestaurants: Single<List<FireStoreRestaurant>>

    private val userRef: CollectionReference
    private val visitedRef: CollectionReference
    private var currentUser: User? = null

    init {
        userRef = db.collection(USERS_COLLECTION)
        visitedRef = db.collection(VISITED_COLLECTION)
        getUsers()
    }

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
                users.onNext(mapToUser(snapshot.documents))
            } else {
                users.onNext(listOf())
            }
        }

    }

    private fun mapToUser(documents: List<DocumentSnapshot>): List<User> {
        return documents.map {
            val user = it.toObject(User::class.java)!!
            if (user.id == currentUser?.id) currentUser = user
            user
        }
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

        private const val PICKED_TODAY = "pickedToday"

        private const val PICKED_RESTAURANT = "pickedRestaurant"
        const val CURRENT_PICKED = "changed"
        const val CURRENT_NOT_PICKED = "deleted"
    }
}
