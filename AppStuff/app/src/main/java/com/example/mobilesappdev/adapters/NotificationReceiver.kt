package com.example.mobilesappdev.adapters

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mobilesappdev.App
import com.example.mobilesappdev.App.Companion.CHANNEL_ID_1
import com.example.mobilesappdev.MainActivity
import com.example.mobilesappdev.R
import java.util.*

class NotificationReceiver : BroadcastReceiver() {

    private var noBoot : Boolean? = false
    lateinit var reminders : Reminders


    companion object {
        lateinit var reminder : Reminders
        var main = MainActivity
    }

    //lateinit var userList : ArrayList<Reminders>
    //lateinit var notifBuilder : NotificationCompat.Builder
    @JvmOverloads
    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationManager : NotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val ID = intent?.getStringExtra("NotifID")?.toInt()


        val intent1 : Intent = Intent(context.applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }


        val taskStackBuilder : TaskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(MainActivity::class.java)
        taskStackBuilder.addNextIntent(intent1)

        val pendingIntent2 : PendingIntent = taskStackBuilder.getPendingIntent(
            ID!!,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val bMap = BitmapFactory.decodeResource(context.resources,R.drawable.ic_new_logo)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_1)
            .setContentTitle(intent?.getStringExtra("Title").toString())
            .setContentText(intent?.getStringExtra("Message").toString()).setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
            .setContentIntent(pendingIntent2)
            .setChannelId(App.CHANNEL_ID_1)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            .setDefaults(NotificationCompat.FLAG_BUBBLE)
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS )
            .setDefaults(NotificationCompat.DEFAULT_SOUND)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)



        with(NotificationManagerCompat.from(context)){
            notify(ID!!, notification.build())
        }.apply {

            reminders = Reminders(intent?.getStringExtra("Title").toString(), intent?.getStringExtra("Message").toString(),Date(intent?.getStringExtra("RemindDate")))


            //val i: Int = 0
            for (i in 0 until main.userList!!.size) {
                if (main.userList!![i] == reminders) {
                    main.userList!!.removeAt(i)
                    break;
                }
            }

            main.cCheck()
            main.reminderadapter3.notifyDataSetChanged()
        }


    }
}