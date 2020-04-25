package com.retar.go4lunch.manager.notificationManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import com.retar.go4lunch.R
import com.retar.go4lunch.ui.MainActivity
import dagger.android.support.DaggerAppCompatActivity

class NotificationHelper(private val context: Context) {

    fun createNotification(title:String, id:String) {

        createNotificationChannel()

        val builder = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_restaurant_menu)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_subtitle, title))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(getPendingIntent(title, id))
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(PICKED_RESTAURANT_REMINDER_ID, builder.build())

        }

    }


    private fun getPendingIntent(title:String, id:String) : PendingIntent {

        val bundle = Bundle().apply {
            putString("id", id)
            putString("title", title)
        }
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.navigation_graph)
            .setDestination(R.id.restaurantDetailFragment)
            .setArguments(bundle)
            .createPendingIntent()
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel =
                NotificationChannel(context.getString(R.string.channel_id), name, importance)
            mChannel.description = descriptionText
            val notificationManager =
                context.getSystemService(DaggerAppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }


    companion object {
        private const val PICKED_RESTAURANT_REMINDER_ID = 12
    }

}
