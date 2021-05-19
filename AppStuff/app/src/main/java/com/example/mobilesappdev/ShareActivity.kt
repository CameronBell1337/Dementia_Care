package com.example.mobilesappdev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ShareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        toolBarSetup()
    }

    private fun toolBarSetup()
    {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id._toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.share_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}