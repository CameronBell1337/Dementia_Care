package com.example.mobilesappdev.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.mobilesappdev.R

class Story05 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story05)
        closeButton()
    }

    private fun closeButton()
    {
        val closeButton : ImageButton = findViewById(R.id.cardCloseButton)

        closeButton?.setOnClickListener() { _view : View ->

            closeButton.visibility = View.INVISIBLE
            super.onBackPressed()
        }

    }
}