package com.example.pilotlogbook.data.room.entities.dailyflight.tuples

import androidx.room.ColumnInfo

data class SyntheticTrainingDevicesSessionTuple(
    @ColumnInfo(name = "synthetic_date")val date: String?,
    val type: String?,
    val totalTimeOfSession: Double?
)
