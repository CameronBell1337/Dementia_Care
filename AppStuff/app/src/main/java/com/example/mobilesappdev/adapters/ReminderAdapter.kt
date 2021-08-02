package com.example.mobilesappdev.adapters

import android.content.Context
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilesappdev.R

//The Schedule class for the Dynamic Recycler View
class ReminderAdapter(val c: Context, val userList:List<Reminders>) :
    RecyclerView.Adapter<ReminderAdapter.MyViewHolder>() {


    inner class MyViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var Summary = v.findViewById<TextView>(R.id.summaryReminder)
        var Title = v.findViewById<TextView>(R.id.titleReminderReminder)
        var RemindDate = v.findViewById<TextView>(R.id.dateTime)

        init {
            Summary = itemView.findViewById(R.id.summaryReminder)
            RemindDate = itemView.findViewById(R.id.dateTime)
            Title = itemView.findViewById(R.id.titleReminderReminder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.reminder_card, parent, false)
        return MyViewHolder(v)
    }


    override fun onBindViewHolder(holder: MyViewHolder, i: Int) {
        val reminders = userList[i]
        holder.Title.text  = reminders.Title
        holder.Summary.text  = reminders.Summary
        holder.RemindDate.text  = reminders!!.RemindDate.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}