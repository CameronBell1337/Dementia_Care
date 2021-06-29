package com.example.mobilesappdev.adapters

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.text.style.UpdateAppearance
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.example.mobilesappdev.App.Companion.CHANNEL_ID_1
import com.example.mobilesappdev.App.Companion.CHANNEL_ID_2
import com.example.mobilesappdev.MainActivity
import com.example.mobilesappdev.R

class NotificationReceiver : BroadcastReceiver() {

    @JvmOverloads
    override fun onReceive(context: Context?, intent: Intent?) {

        var notificationManager : NotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val r_i = Intent(context.applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        var pI : PendingIntent = PendingIntent.getActivity(context, 10, r_i, PendingIntent.FLAG_UPDATE_CURRENT)


        val bitmapS = BitmapFactory.decodeResource(context.resources, R.drawable.story00_02)
        val bitmapL = BitmapFactory.decodeResource(context.resources, R.drawable.ic_new_logo)
        val b = NotificationCompat.Builder(context, CHANNEL_ID_1)
            .setContentIntent(pI)
            .setSmallIcon(R.drawable.ic_new_logo)
            .setContentTitle("James is doing this shit")
            .setContentText("YEEEET")
            .setLargeIcon(bitmapL)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmapL))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)


        /*with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(10, b.build())
        }*/


        val r_i2 = Intent(context.applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        var pI2 : PendingIntent = PendingIntent.getActivity(context, 20, r_i2, PendingIntent.FLAG_UPDATE_CURRENT)

        val bitmapS2 = BitmapFactory.decodeResource(context.resources, R.drawable.story00_02)
        val bitmapL2 = BitmapFactory.decodeResource(context.resources, R.drawable.ic_new_logo)
        val b2 = NotificationCompat.Builder(context, CHANNEL_ID_2)
            .setContentIntent(pI2)
            .setSmallIcon(R.drawable.ic_new_logo)
            .setContentTitle("James is doing this shit 2")
            .setContentText("BOIIIIIII")
            .setLargeIcon(bitmapL)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmapL))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(20, b2.build())
            notify(10, b.build())
        }



    }

    public fun channel1()
    {

    }
}