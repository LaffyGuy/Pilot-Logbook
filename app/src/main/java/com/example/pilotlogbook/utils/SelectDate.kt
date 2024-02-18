package com.example.pilotlogbook.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.Calendar

class SelectDate(private val context: Context) {

    private val calendar = Calendar.getInstance()

    fun showDatePicker(updateLabel: (Calendar) -> Unit) {
        val datePickerDialog =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                updateLabel(calendar)
            }

        DatePickerDialog(
            context,
            datePickerDialog,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

}


class SelectTime(private val context: Context){

    private val calendar = Calendar.getInstance()

    fun showTimePickerDialog(updateLabel: (Calendar) -> Unit){
        val timePickerDialog = TimePickerDialog.OnTimeSetListener{_, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            updateLabel(calendar)

        }
        TimePickerDialog(
            context,
            timePickerDialog,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

}

