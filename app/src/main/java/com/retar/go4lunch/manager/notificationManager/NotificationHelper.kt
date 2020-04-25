package com.retar.go4lunch.base.notificaitonHelper

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.retar.go4lunch.R
import dagger.android.support.DaggerAppCompatActivity

class NotificationHelper(val context: Context) {

    fun createNotification() {

        createNotificationChannel()

//        val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
//            putExtra(MainActivity.EXTRA_IS_NOTIFICATION, true)
//        }

//        val pendingMainActivityIntent = PendingIntent.getActivity(context, 0, mainActivityIntent, 0)


        val builder = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
            .setSmallIcon(R.drawable.ic_restaurant_menu)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_subtitle, "test"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingMainActivityIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(PICKED_RESTAURANT_REMINDER_ID, builder.build())

        }

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
