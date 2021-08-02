package com.example.mobilesappdev.adapters

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mobilesappdev.App
import com.example.mobilesappdev.App.Companion.CHANNEL_ID_1
import com.example.mobilesappdev.MainActivity
import com.example.mobilesappdev.R
import java.util.*

class NotificationReceiver : BroadcastReceiver() {

    lateinit var reminders : Reminders

    //Sets static variables needed to reach from other classes
    companion object {
        lateinit var reminder : Reminders
        var main = MainActivity
    }

    //lateinit var userList : ArrayList<Reminders>
    //lateinit var notifBuilder : NotificationCompat.Builder
    @JvmOverloads
    override fun onReceive(context: Context?, intent: Intent?) {
        var notificationManager : NotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /*Grabs the Unique ID for the current notification being scheduled and assigning each scheduled notification its own notificationCompat.builder
        so you can have more than one notification without them being cancelled out via other notifications
         */
        val ID = intent?.getStringExtra("NotifID")?.toInt()


        //creates the local variable using the intent pass through from MainActivity
        val intent1 : Intent = Intent(context.applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }


        val alarmsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        //Used for creating more than one type of notification
        val taskStackBuilder : TaskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(MainActivity::class.java)
        taskStackBuilder.addNextIntent(intent1)

        //Gets the pending intent
        val pendingIntent2 : PendingIntent = taskStackBuilder.getPendingIntent(
            ID!!,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val bMap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_new_launcher)

        //Builds the notification to be sent
        val notification = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NotificationCompat.Builder(context, CHANNEL_ID_1)
                .setLargeIcon(bMap)
                .setContentTitle(intent?.getStringExtra("Title").toString())
                .setContentText(intent?.getStringExtra("Message").toString()).setAutoCancel(true)
                .setSmallIcon(R.drawable.notification_icon)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setContentIntent(pendingIntent2)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setColor(context.getColor(R.color.primaryColor))
        } else {
            NotificationCompat.Builder(context, CHANNEL_ID_1)
                .setContentTitle(intent?.getStringExtra("Title").toString())
                .setContentText(intent?.getStringExtra("Message").toString()).setAutoCancel(true)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentIntent(pendingIntent2)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setColor(Color.RED)
        }




        //Send the notification
        with(NotificationManagerCompat.from(context)){
            notify(ID!!, notification.build())
        }.apply {

            //This is for functions which are to only be executed once the notification sends
            reminders = Reminders(
                intent?.getStringExtra("Title").toString(), intent?.getStringExtra(
                    "Message"
                ).toString(), Date(intent?.getStringExtra("RemindDate"))
            )


            //Loops through all current userList array data and removes the Reminder() for each notification which has been sent
            //val i: Int = 0
            for (i in 0 until main.userList!!.size) {
                if (main.userList!![i] == reminders) {
                    main.userList!!.removeAt(i)
                    break;
                }
            }

            //updates the UI and calls to change the Recycler view containing the card views to
            main.cCheck()
            main.reminderadapter3.notifyDataSetChanged()
        }


    }
}