package com.example.pilotlogbook.data.room.entities.dailyflight.tuples

import androidx.room.ColumnInfo

data class ArrivalTuple(
    @ColumnInfo(name = "arrival_place")val place: String,
    @ColumnInfo(name = "arrival_time")val time: Double
)