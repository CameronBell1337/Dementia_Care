package com.example.mobilesappdev

import android.app.*
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.mobilesappdev.adapters.*
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var layoutManager1: RecyclerView.LayoutManager? = null
    private var layoutManager2: RecyclerView.LayoutManager? = null
    private var adapter1: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null
    private var adapter2: RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder>? = null


    //reminder setup variables as static to be referenced from
    companion object {
        private lateinit var addsBtn: Button
        private lateinit var recyclerView3: RecyclerView
        lateinit var reminderadapter3: RecyclerView.Adapter<ReminderAdapter.MyViewHolder>
        var userList: ArrayList<Reminders>? = null
        private lateinit var confirmBtn: Button
        private lateinit var selectBtn: Button
        private lateinit var newCalender: Calendar
        private lateinit var removeBtn: Button
        lateinit var noReminder : TextView
        var Ids : Int = 0
        var saveB : Boolean = false


        //Little dirty cloning the functions but to allow notification reciever to be able to access them
        fun cCheck()
        {
            //Formats time to mills and compares to sytme clock
            val givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013"
            val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
            val currentTime = System.currentTimeMillis()


            //Loops thourgh all userList data and compares set time via current time to see if any has is older than current time
            val iterator = (0..userList!!.size).iterator()
            iterator.forEach {
                for (i in 0 until userList!!.size) {
                    val checkTime = sdf.parse(userList!![i].RemindDate.toString())
                    val endTime = checkTime.time
                    if (currentTime > endTime) {
                        userList!!.removeAt(i)
                        reminderadapter3.notifyDataSetChanged()

                        //SavedData()
                    }
                    if (i >= userList!!.size) {
                        break;
                    }
                }
            }

            cSetItemsInRecycler()
        }

        //Changes the sub title with amount of reminders currently being set
        fun cSetItemsInRecycler() {

            if (userList!!.size > 0) {
                if (userList!!.size == 1) {
                    noReminder!!.text = "You have ${userList!!.count().toString()} reminder currently. "
                } else if (userList!!.size >= 2) {
                    noReminder.text = "You have ${userList!!.count().toString()} reminders currently. "
                }
            } else {
                noReminder.text = "Nothing Scheduled."
            }
            //SavedData()
        }
    }


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text: TextView = findViewById(R.id.websiteLink)
        text.movementMethod = LinkMovementMethod.getInstance()

        setup()

        text.movementMethod = LinkMovementMethod.getInstance()


        addsBtn.setOnClickListener { addReminder() }
        removeBtn.setOnClickListener { check()}

        //kind dirty to being called every tick rather than on an action but only work around for now
        val iterator = (0..userList!!.size).iterator()
        iterator.forEach {
            check()
        }

    }


    fun check()
    {
        //Formats time to mills and compares to sytme clock
        val givenDateString = "Tue Apr 23 16:08:28 GMT+05:30 2013"
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
        val currentTime = System.currentTimeMillis()

        for (i in 0 until userList!!.size) {
                val checkTime = sdf.parse(userList!![i].RemindDate.toString())
                val endTime = checkTime.time
                if (currentTime > endTime) {
                    userList!!.removeAt(i)
                    reminderadapter3.notifyDataSetChanged()
                    setItemsInRecycler()
                    SavedData()
                }
            break;

            }



    }

    private fun setup() {

        LoadData()
        recylerViewSetup()
        instFragment()
        themeChange()
        drawerLayoutSetup()
        reminderViewSetup()
        setItemsInRecycler()

    }


    private fun reminderViewSetup() {
        val snapHelper1: SnapHelper = LinearSnapHelper()
        noReminder = findViewById(R.id._noReminders)
        Ids = 0
        addsBtn = findViewById(R.id.addFButton)
        removeBtn = findViewById(R.id.dataRemoveBtn)
        reminderadapter3 = ReminderAdapter(this, userList!!)
        recyclerView3 = findViewById(R.id._remindersView)
        recyclerView3.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView3.adapter = reminderadapter3
        snapHelper1.attachToRecyclerView(recyclerView3)
    }


    fun setItemsInRecycler() {

        if (userList!!.size > 0) {
            if (userList!!.size == 1) {
                noReminder!!.text = "You have ${userList!!.count().toString()} reminder currently. "
            } else if (userList!!.size >= 2) {
                noReminder.text = "You have ${userList!!.count().toString()} reminders currently. "
            }
        } else {
            noReminder.text = "Nothing Scheduled."
        }
        //SavedData()
    }


    //Add button is pressed calling this fun to open Dialog box
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addReminder() {
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_reminder_card, null)
        val addDialog = Dialog(this)
        val titleS = v.findViewById<EditText>(R.id.editTitle)
        val date: TextView = v.findViewById(R.id.date)
        val summary = v.findViewById<EditText>(R.id.editMessage)


        selectBtn = v.findViewById(R.id.selectDateBtn)
        confirmBtn = v.findViewById(R.id.addEventButton)


        //Opens another DialogAlert containing a Date and timer picker to specific time to set reminder at
        addDialog.setContentView(v)
        newCalender = Calendar.getInstance()
        selectBtn.setOnClickListener {
            val dialog = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val newDate = Calendar.getInstance()
                    val newTime = Calendar.getInstance()
                    val time = TimePickerDialog(
                        this,
                        { _, hourOfDay, minute ->
                            newDate[year, month, dayOfMonth, hourOfDay, minute] = 0
                            val temp = Calendar.getInstance()
                            if (newDate.timeInMillis - temp.timeInMillis > 0)
                                date.text = (newDate.time.toString()
                                        )
                            else {
                                date.hint = "Date and Time"
                                Toast.makeText(this, "Invalid time", Toast.LENGTH_SHORT).show()
                            }
                        },
                        newTime[Calendar.HOUR_OF_DAY],
                        newTime[Calendar.MINUTE], true
                    )
                    time.show()
                },
                newCalender[Calendar.YEAR],
                newCalender[Calendar.MONTH], newCalender[Calendar.DAY_OF_MONTH]
            )
            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.show()
        }

        //Confirms everything and pass through the data and saved the reminder
        confirmBtn.setOnClickListener {

            //val dialog = Dialog as DialogInterface
            val title = titleS.text
            val message = summary.text
            val dates = date.text
            val reminders: Reminders
            if (title.toString() != "" && message.toString() != "" && dates.toString() != "") {
                val remind = Date(dates.toString().trim { it <= ' ' })
                val calendar: Calendar = Calendar.getInstance()
                calendar.time = remind
                calendar.set(Calendar.SECOND, 0)
                userList!!.add(Reminders(title.toString(), message.toString(), remind))
                val l: ArrayList<Reminders> = userList!!
                reminders = l[l.size - 1]
                SavedData()
                reminderadapter3.notifyDataSetChanged()
                val intent = Intent(this, NotificationReceiver::class.java)
                intent.putExtra("Title", reminders.Title)
                intent.putExtra("Message", reminders.Summary)
                intent.putExtra("RemindDate", reminders.RemindDate.toString())
                intent.putExtra("NotifID", Ids.toString())
                val intent1 = PendingIntent.getBroadcast(
                    this,
                    Ids,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, intent1)
                setItemsInRecycler()
                Ids++
                addDialog.dismiss()
            } else {
                if (title.toString() == "") {
                    Toast.makeText(this, "Missing Title", Toast.LENGTH_SHORT).show()
                } else if (message.toString() == "") {
                    Toast.makeText(this, "Missing Summary", Toast.LENGTH_SHORT).show()
                } else if (date.toString() == "") {
                    Toast.makeText(this, "Missing Time & Date", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Missing Fields", Toast.LENGTH_SHORT).show()
                }
            }
        }

        addDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addDialog.create()
        addDialog.show()


    }

    //Loads data saved in shared preferences via a json parse
    fun LoadData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            "shared prefereces",
            MODE_PRIVATE
        )


        val gson: Gson = Gson()
        val json: String? = sharedPreferences.getString("reminder list", null)
        val type: Type = object : TypeToken<ArrayList<Reminders>>() {}.type

        userList = gson.fromJson(json, type)



        if (userList == null) {
            userList = ArrayList()
        }

        check()
    }

    //Saves data to shared preferences via Json parse
    public fun SavedData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(
            "shared prefereces",
            MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson: Gson = Gson()

        val json: String = gson.toJson(userList)
        editor.putString("reminder list", json)
        editor.apply()
    }




    public fun RemoveData() {

        val i: Int = 0
        for (i in 0 until userList!!.size) {
            userList!!.removeAt(i)
            break;
        }
        reminderadapter3.notifyDataSetChanged()
        setItemsInRecycler()
        SavedData()

    }

    //Set up app drawer
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


    //Sets all recylerViews and assigns specific layoutManagers and snap helpers
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


    //Sets the animations for open and closing the drawMenu
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


    //Rather than exit the application while the menu drawer is open just closes it if open = true
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    //Fragments currently not in use as of the moment
    private fun instFragment() {
        //val viewPager: ViewPager2 = findViewById(R.id._viewPager)
        val fragments: ArrayList<Fragment> = arrayListOf(

        )
        val adapter = ViewPagerAdapter(fragments, this)


        //viewPager.adapter = adapter
    }

    //Controls the night and dark theme and assigns it to the getpreferences Appearance
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
        } else if (prefs?.toInt() == 3) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                drawerLayout.setScrimColor(resources.getColor(R.color.darkGrey))
            } else {
                drawerLayout.setScrimColor(resources.getColor(R.color.grey))
            }
        }

        sharedPrefsEdit.apply()

    }

    //Sets up interaction on the navigation drawer
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