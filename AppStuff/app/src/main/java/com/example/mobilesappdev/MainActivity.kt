package com.example.mobilesappdev

import android.app.*
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
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
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var layoutManager1: RecyclerView.LayoutManager? = null
    private var layoutManager2: RecyclerView.LayoutManager? = null
    private var adapter1: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null
    private var adapter2: RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder>? = null


    //reminder setup
    private lateinit var addsBtn : Button
    private lateinit var recyclerView3: RecyclerView
    private lateinit var reminderadapter3: RecyclerView.Adapter<ReminderAdapter.MyViewHolder>
    private lateinit var userList: ArrayList<Reminders>
    private lateinit var confirmBtn: Button
    private lateinit var selectBtn: Button


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text: TextView = findViewById(R.id.websiteLink)
        text.movementMethod = LinkMovementMethod.getInstance()
        //appDatabase = AppDatabase.geAppdatabase(this)!!




        setup()
        reminderViewSetup()
        text.movementMethod = LinkMovementMethod.getInstance()

        addsBtn.setOnClickListener { addReminder() }
    }
    private fun setup() {

        recylerViewSetup()
        instFragment()
        themeChange()
        drawerLayoutSetup()



    }

    private fun reminderViewSetup() {

        val snapHelper1: SnapHelper = LinearSnapHelper()
        userList = ArrayList<Reminders>()
        addsBtn = findViewById(R.id.addFButton)
        reminderadapter3 = ReminderAdapter(this, userList)
        recyclerView3 = findViewById(R.id._remindersView)
        recyclerView3.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView3.adapter = reminderadapter3
        snapHelper1.attachToRecyclerView(recyclerView3)
    }



    fun setItemsInRecycler()
    {
        if(userList.size>0)
        {

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addReminder() {
        val inflater : LayoutInflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_reminder_card, null)
        val addDialog = Dialog(this)
        val titleS = v.findViewById<EditText>(R.id.editTitle)
        val summary = v.findViewById<EditText>(R.id.editMessage)
        val dialog : Dialog

        selectBtn = v.findViewById(R.id.selectDateBtn)
        confirmBtn = v.findViewById(R.id.addEventButton)

        addDialog.setContentView(v)
        confirmBtn.setOnClickListener{
            val title = titleS.text
            val message = summary.text
            if(title.toString() != "" && message.toString() != "") {
                userList.add(Reminders(title.toString(), message.toString()))
                reminderadapter3.notifyDataSetChanged()
                addDialog.dismiss()
            }
            else
            {
                Toast.makeText(this, "Please fill out ALL fields", Toast.LENGTH_SHORT).show()
            }

        }


        selectBtn.setOnClickListener{

            //val dialog = Dialog as DialogInterface
            val title = titleS.text
            val message = summary.text
            userList.add(Reminders(title.toString(), message.toString()))
            reminderadapter3.notifyDataSetChanged()
            addDialog.dismiss()
        }
        addDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    addDialog.create()
    addDialog.show()
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