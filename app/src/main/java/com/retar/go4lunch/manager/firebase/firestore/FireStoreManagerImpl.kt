package com.retar.go4lunch.manager.firebase.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.retar.go4lunch.R
import com.retar.go4lunch.base.model.RateModel
import com.retar.go4lunch.base.model.RestaurantEntity
import com.retar.go4lunch.base.model.User
import com.retar.go4lunch.manager.firebase.firestore.model.FireStoreRestaurant
import de.aaronoe.rxfirestore.getCompletable
import de.aaronoe.rxfirestore.getObservable
import de.aaronoe.rxfirestore.getSingle
import io.reactivex.Observable
import io.reactivex.Single

class FireStoreManagerImpl(
    db: FirebaseFirestore
) {

    private val userRef: CollectionReference
    private val visitedRef: CollectionReference
    private val ratingRef: CollectionReference
    var currentUser: User? = null

    init {
        userRef = db.collection(USERS_COLLECTION)
        visitedRef = db.collection(VISITED_COLLECTION)
        ratingRef = db.collection(RATING_COLLECTION)
    }

    fun saveUser(user: User) {
        userRef.document(user.id).set(user)
    }

    fun getFireStoreUser(id: String) {
        userRef.document(id).addSnapshotListener { documentSnapshot, _ ->
            documentSnapshot?.let {
                currentUser = documentSnapshot.toObject(User::class.java)
            }
        }
    }


    fun getUsers(): Observable<List<User>> {
        return userRef.getObservable()
    }

    private fun getRatings(): Observable<List<RateModel>> {
        return ratingRef.getObservable()
    }

    fun mapWithVisitedRestaurantsAndRating(restaurantsEntity: List<RestaurantEntity>): Observable<List<RestaurantEntity>> {
        return visitedRef.getSingle<FireStoreRestaurant>()
            .map { visitedRestaurants ->
                visitedRestaurants.map { visitedRestaurant ->
                    visitedRestaurant.id
                }
            }
            .map { visitedIds ->
                restaurantsEntity.map {
                    if (it.id in visitedIds) it.icon = R.drawable.ic_restaurant_marker_green
                    it
                }
            }
            .flatMap { restaurant ->
                userRef.getSingle<User>()
                    .map {
                        mapWithSelectedRestaurants(it, restaurant)
                    }


            }
            .flatMapObservable { restaurants ->
                getRatings()
                    .map {
                        mapWithRating(it, restaurants)
                    }
            }
            .flatMap { restaurants ->
                getUsers()
                    .map {
                        mapUsersWithRestaurants(it, restaurants)
                    }
            }


    }

    private fun mapUsersWithRestaurants(
        users: List<User>,
        restaurants: List<RestaurantEntity>
    ): List<RestaurantEntity> {
        return restaurants.map { restaurant ->
            val listOfPicked = users.filter { user ->
                user.pickedRestaurant == restaurant.id
            }
            restaurant.timesPicked = listOfPicked.size
            restaurant
        }
    }

    private fun mapWithRating(
        ratings: List<RateModel>,
        restaurants: List<RestaurantEntity>
    ): List<RestaurantEntity> {
        return restaurants.map { restaurant ->
            val rateModel = ratings.find { rateModel ->
                rateModel.id == restaurant.id
            }

            restaurant.rating = rateModel?.rating
            restaurant

        }
    }

    private fun mapWithSelectedRestaurants(
        users: List<User>,
        restaurants: List<RestaurantEntity>
    ): List<RestaurantEntity> {
        return restaurants.map { restaurant ->
            val listOfPicked = users.filter {
                it.pickedRestaurant == restaurant.id
            }
            restaurant.timesPicked = listOfPicked.size
            restaurant
        }
    }


    fun onRestaurantPicked(id: String, title: String): Single<String> {
        return if (isCurrentPicked(id)) {
            updatePickedRestaurant("", "")
            Single.just(CURRENT_NOT_PICKED)
        } else {
            updatePickedRestaurant(id, title)
            Single.just(CURRENT_PICKED)
        }
    }


    private fun updatePickedRestaurant(id: String, title: String) {
        currentUser?.let {
            userRef.document(it.id).update(mapOf(PICKED_RESTAURANT to id, PICKED_RESTAURANT_TITLE to title))

        }
    }

    fun checkIfPicked(id: String): Boolean {
        return isCurrentPicked(id)
    }

    private fun isCurrentPicked(id: String): Boolean {
        return (id == currentUser?.pickedRestaurant ?: false)
    }

    fun getCurrentRating(): Single<List<RateModel>> {
        return ratingRef.getSingle()
    }

    fun addRating(rateModel: RateModel) {
        ratingRef.document(rateModel.id).set(rateModel)
    }

    fun updateRating(rateModel: RateModel) {
        ratingRef.document(rateModel.id).set(rateModel)
    }


    companion object {
        private const val USERS_COLLECTION = "users"
        private const val VISITED_COLLECTION = "visitedRestaurants"
        private const val RATING_COLLECTION = "ratingCollection"

        private const val PICKED_RESTAURANT = "pickedRestaurant"
        private const val PICKED_RESTAURANT_TITLE = "pickedRestaurantTitle"
        const val CURRENT_PICKED = "changed"
        const val CURRENT_NOT_PICKED = "deleted"
    }
}
