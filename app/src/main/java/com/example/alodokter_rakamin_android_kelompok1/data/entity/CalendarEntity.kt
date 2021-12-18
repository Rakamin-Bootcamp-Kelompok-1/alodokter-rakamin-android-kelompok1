package com.example.alodokter_rakamin_android_kelompok1.data.entity

import java.text.SimpleDateFormat
import java.util.*

data class CalendarEntity(var data: Date, var isSelected: Boolean = false) {

    val calendarDay: String
        get() = SimpleDateFormat("EE", Locale.getDefault()).format(data)

    val calendarDate: String
        get() {
            val cal = Calendar.getInstance()
            cal.time = data
            return cal[Calendar.DAY_OF_MONTH].toString()
        }
}