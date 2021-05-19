package com.example.mobilesappdev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UsefulContactsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_useful_contacts)
        toolBarSetup()
    }

    private fun toolBarSetup()
    {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id._toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.useful_contacts_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}