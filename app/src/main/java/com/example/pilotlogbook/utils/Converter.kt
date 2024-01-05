package com.example.pilotlogbook.utils

import java.text.SimpleDateFormat
import java.util.Locale


fun convertLongToTime(value: Long): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    return formatter.format(value)
}


fun convertLongToDate(value: Long): String{
     val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

     return formatter.format(value)
}
