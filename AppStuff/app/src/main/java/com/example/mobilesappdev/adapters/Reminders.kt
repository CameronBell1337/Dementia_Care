package com.example.mobilesappdev.adapters

import androidx.room.Entity
import androidx.room.PrimaryKey

import java.util.Date


data class Reminders(


   public var Title: String,
   public var Summary: String,
   public var RemindDate: Date
)


