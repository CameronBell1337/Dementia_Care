package com.example.mobilesappdev.adapters

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.text.style.UpdateAppearance
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.example.mobilesappdev.App.Companion.CHANNEL_ID_1
import com.example.mobilesappdev.App.Companion.CHANNEL_ID_2
import com.example.mobilesappdev.MainActivity
import com.example.mobilesappdev.R

class NotificationReceiver : BroadcastReceiver() {

    private var noBoot : Boolean? = false
    @JvmOverloads
    override fun onReceive(context: Context?, intent: Intent?) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent?.action))
        {
             noBoot = intent?.getBooleanExtra(Intent.ACTION_BOOT_COMPLETED, false)
            Toast.makeText(context, "Boot Completed", Toast.LENGTH_SHORT).show()

            if(noBoot == true)
            {
                Toast.makeText(context, "Boot Completed", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(context, "Boot uncompleted", Toast.LENGTH_SHORT).show()
            }
        }


    }

    public fun channel1()
    {

    }
}