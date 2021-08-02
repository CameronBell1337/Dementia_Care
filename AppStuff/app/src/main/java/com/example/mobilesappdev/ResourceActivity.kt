package com.example.mobilesappdev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.mobilesappdev.adapters.RecyclerViewAdapter2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_resource.*

class ResourceActivity : AppCompatActivity() {

    private var layoutManager1: RecyclerView.LayoutManager? = null
    private var adapter1: RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)
        toolBarSetup()
        recylerViewSetup()
    }

    private fun toolBarSetup()
    {
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id._toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.resources_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun recylerViewSetup() {
        // Layout Managers //
        layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        resourcedRecyler.layoutManager = layoutManager1
        resourcedRecyler.layoutManager?.paddingTop
        // Recycler View Adapters //
        adapter1 = RecyclerViewAdapter2()
        // Adapters //
        resourcedRecyler.adapter = adapter1
        // Snap helpers //

    }


}