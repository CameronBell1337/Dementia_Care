package com.example.mobilesappdev

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
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

    private fun createNotificationChannels()
    {
        //Creates different notification channels and check is the sdk build
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            var channel1 : NotificationChannel = NotificationChannel(
                CHANNEL_ID_1, "Channel 1", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel1.description = "This is Channel 1"

            var channel2 : NotificationChannel = NotificationChannel(
                CHANNEL_ID_2, "Channel 2", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel2.description = "This is Channel 2"

            var nManager : NotificationManager = getSystemService(NotificationManager::class.java)
            nManager.createNotificationChannel(channel1)
            nManager.createNotificationChannel(channel2)
        }
    }
}