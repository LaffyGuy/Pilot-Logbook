package com.example.pilotlogbook.data.room.entities.dailyflight.tuples

import androidx.room.ColumnInfo

data class DepartureTuple(
    @ColumnInfo(name = "departure_place")val place: String?,
    @ColumnInfo(name = "departure_time")val time: Long?
)
