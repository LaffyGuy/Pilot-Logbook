package com.example.pilotlogbook.data.room.entities.dailyflight.tuples

import androidx.room.ColumnInfo

data class LandingsTuple(
    val day: Int? = null,
    @ColumnInfo(name = "landings_night")val night: Int? = null
)
