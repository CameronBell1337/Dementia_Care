package com.example.mobilesappdev

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesappdev.adapters.RecyclerViewAdapter
import com.example.mobilesappdev.adapters.ViewPagerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instFragment()
        themeChange()
        setup()
    }

    private fun setup() {
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



        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        _recyclerView.layoutManager = layoutManager

        adapter = RecyclerViewAdapter()
        _recyclerView.adapter = adapter
    }

    private fun drawAnimations()
    {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val contentView : ScrollView = findViewById(R.id._contentView)
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
        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)

        val prefs = appSettingsPref?.getString("Appearance", "1")
        if(prefs?.toInt() == 1)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            drawerLayout.setScrimColor(resources.getColor(R.color.grey))
        }
        else if (prefs?.toInt() == 2)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            drawerLayout.setScrimColor(resources.getColor(R.color.darkGrey))
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            {
                drawerLayout.setScrimColor(resources.getColor(R.color.darkGrey))
            }
            else
            {
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