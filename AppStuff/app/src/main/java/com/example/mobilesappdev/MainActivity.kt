package com.example.mobilesappdev

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.mobilesappdev.App.Companion.CHANNEL_ID_1
import com.example.mobilesappdev.adapters.NotificationReceiver
import com.example.mobilesappdev.adapters.RecyclerViewAdapter
import com.example.mobilesappdev.adapters.RecyclerViewAdapter1
import com.example.mobilesappdev.adapters.ViewPagerAdapter
import com.example.mobilesappdev.fragments.Story01
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var layoutManager1: RecyclerView.LayoutManager? = null
    private var layoutManager2: RecyclerView.LayoutManager? = null
    private var adapter1: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null
    private var adapter2: RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder>? = null
    var alarmManager : AlarmManager? = null

    lateinit var pI1 : PendingIntent
    lateinit var pI2 : PendingIntent

    public var channel_ID = "channel_id_01"
    private val notificationID = 101


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text : TextView = findViewById(R.id.websiteLink)
        text.movementMethod = LinkMovementMethod.getInstance()

        setup()
        //createNotifChannel()
        notificationTrigger()

    }


    private fun notificationTrigger()
    {
        val notifB1 : MaterialButton = findViewById(R.id.notification1Button)
        val notifB2 : MaterialButton = findViewById(R.id.notification2Button)
        val cNotifB : MaterialButton = findViewById(R.id.cancelnotificationButton)


        notifB1.setOnClickListener{
            sendNotifications1()
        }

        notifB2.setOnClickListener{
            sendNotifications2()
        }

        cNotifB.setOnClickListener{
            cancelNotifications()
        }
    }


    private fun createNotifChannel()
    {
        //Security measure to make sure android oreo and higher still receive the notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val notifTitle = "Notification Title"
            val notifDescTxt = "Notification Description"
            val i = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID_1, notifTitle, i).apply { description = notifDescTxt }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }

    private fun sendNotifications1()
    {
        alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager

        val i  = Intent(this, NotificationReceiver::class.java)
        i.action = "MY_NOTIFICATION_MESSAGE"
        pI1 = PendingIntent.getBroadcast(this, 10, i, PendingIntent.FLAG_UPDATE_CURRENT)


        val currentTime = System.currentTimeMillis()
        val calender : Calendar = Calendar.getInstance()
        val interval : Long = 1000 * 10

        alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP, currentTime,  interval, pI1)
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calender.timeInMillis + tenSecs, pI)


    }

    private fun sendNotifications2()
    {

        val i2  = Intent(this, NotificationReceiver::class.java)
        i2.action = "MY_NOTIFICATION_MESSAGE2"
        pI2 = PendingIntent.getBroadcast(this, 20, i2, PendingIntent.FLAG_UPDATE_CURRENT)


        val currentTime = System.currentTimeMillis()
        val calender : Calendar = Calendar.getInstance()
        val interval : Long = 1000 * 15


        alarmManager?.setRepeating(AlarmManager.RTC_WAKEUP, currentTime,  interval, pI2)
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calender.timeInMillis + tenSecs, pI)


    }


    private fun cancelNotifications()
    {
            alarmManager?.cancel(pI1)
            alarmManager?.cancel(pI2)
    }

    private fun setup() {
        recylerViewSetup()
        instFragment()
        themeChange()
        drawerLayoutSetup()
    }

    private fun drawerLayoutSetup() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id._toolbar)

        setSupportActionBar(toolbar)

        navView.bringToFront()
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        navView.setCheckedItem(R.id._HomeMenuItem)

        drawAnimations()
    }


    private fun recylerViewSetup() {
        val snapHelper1: SnapHelper = LinearSnapHelper()
        val snapHelper2: SnapHelper = LinearSnapHelper()

        // Layout Managers //
        layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        _demStoryView.layoutManager = layoutManager1
        _petitionView.layoutManager = layoutManager2
        // Recycler View Adapters //
        adapter1 = RecyclerViewAdapter()
        adapter2 = RecyclerViewAdapter1()
        // Adapters //
        _demStoryView.adapter = adapter1
        _petitionView.adapter = adapter2
        // Snap helpers //
        snapHelper1.attachToRecyclerView(_petitionView)
        snapHelper2.attachToRecyclerView(_demStoryView)
    }

    private fun drawAnimations() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val contentView: ScrollView = findViewById(R.id._contentView)
        val _scale = 0.7f


        drawerLayout.addDrawerListener(object : SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                // Scale the View based on current slide offset
                val diffScaledOffset: Float = slideOffset * (1 - _scale)
                val offsetScale = 1 - diffScaledOffset
                contentView.scaleX = offsetScale
                contentView.scaleY = offsetScale

                // Translate the View, accounting for the scaled width
                val xOffset = drawerView.width * slideOffset
                val xOffsetDiff: Float = contentView.getWidth() * diffScaledOffset / 2
                val xTranslation = xOffset - xOffsetDiff
                contentView.translationX = xTranslation
            }
        })
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    private fun instFragment() {
        //val viewPager: ViewPager2 = findViewById(R.id._viewPager)
        val fragments: ArrayList<Fragment> = arrayListOf(

        )
        val adapter = ViewPagerAdapter(fragments, this)


        //viewPager.adapter = adapter
    }

    private fun themeChange() {


        val appSettingsPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val sharedPrefsEdit: SharedPreferences.Editor = appSettingsPref.edit()
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val prefs = appSettingsPref?.getString("Appearance", "3")
        if (prefs?.toInt() == 1) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            drawerLayout.setScrimColor(resources.getColor(R.color.grey))

        } else if (prefs?.toInt() == 2) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            drawerLayout.setScrimColor(resources.getColor(R.color.darkGrey))
        } else if (prefs?.toInt() == 3){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                drawerLayout.setScrimColor(resources.getColor(R.color.darkGrey))
            } else {
                drawerLayout.setScrimColor(resources.getColor(R.color.grey))
            }
        }

        sharedPrefsEdit.apply()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val intent : Intent
        when (item.itemId) {
            R.id._HomeMenuItem -> {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id._resourcesMenuItem -> {
                intent = Intent(this, ResourceActivity::class.java)
                startActivity(intent)
            }
            R.id._contactsMenuItem -> {
                intent = Intent(this, UsefulContactsActivity::class.java)
                startActivity(intent)
            }
            R.id._shareMenuItem -> {
                intent = Intent(this, ShareActivity::class.java)
                startActivity(intent)

            }
            R.id._prefMenuItem -> {
                intent = Intent(this, PreferencesActivity::class.java)
                startActivity(intent)
            }
            R.id._settingMenuItem -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id._helpFeedbackMenuItem -> {
                intent = Intent(this, FeedbackActivity::class.java)
                startActivity(intent)
            }

            else -> return true

        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}