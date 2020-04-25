package com.retar.go4lunch.base

import com.google.firebase.messaging.FirebaseMessagingService

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
