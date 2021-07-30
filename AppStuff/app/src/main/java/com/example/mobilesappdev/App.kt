package com.example.mobilesappdev

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class App : Application() {

    //Creates a static
    companion object
    {
        var CHANNEL_ID_1 :  String = "channel1"
        var CHANNEL_ID_2 : String = "channel2"
    }

    @JvmOverloads
    override fun onCreate() {
        super.onCreate()

        createNotificationChannels()
    }

    //Creates Notification channel as versions of android; Oreo and up require it due to new user permissions
    private fun createNotificationChannels()
    {
        //Creates different notification channels and check is the sdk build
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            var channel1 : NotificationChannel = NotificationChannel(

                CHANNEL_ID_1, "Notifications", NotificationManager.IMPORTANCE_HIGH

            )
            channel1.description = "Schedule Channel"
            channel1.setShowBadge(true)
            channel1.setAllowBubbles(true)
            channel1.hasUserSetImportance()
            channel1.importance = NotificationManager.IMPORTANCE_HIGH
            channel1.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            channel1.canBubble()
            channel1.canShowBadge()

            //Redundant second channel if every needed
            var channel2 : NotificationChannel = NotificationChannel(
                CHANNEL_ID_2, "Channel 2", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel2.description = "This is Channel 2"

            var nManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(channel1)
            nManager.createNotificationChannel(channel2)
        }
    }
}