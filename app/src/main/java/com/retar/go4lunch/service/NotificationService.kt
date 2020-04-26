package com.retar.go4lunch.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.retar.go4lunch.manager.firebase.firestore.FireStoreManager
import com.retar.go4lunch.manager.notificationManager.NotificationHelper
import dagger.android.AndroidInjection
import javax.inject.Inject

class NotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var firestoneManager: FireStoreManager

    @Inject
    lateinit var notificationHelper: NotificationHelper


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val user = firestoneManager.currentUser
        user?.let {
            if(user.pickedRestaurant.isNotEmpty())  notificationHelper.createNotification(user.pickedRestaurantTitle!!, user.pickedRestaurant)
        }
    }

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

}
