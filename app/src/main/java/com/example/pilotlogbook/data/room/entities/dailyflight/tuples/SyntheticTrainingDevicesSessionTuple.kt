package com.example.pilotlogbook.data.room.entities.dailyflight.tuples

import androidx.room.ColumnInfo

data class SyntheticTrainingDevicesSessionTuple(
    @ColumnInfo(name = "synthetic_date")val date: String? = null,
    val type: String? = null,
    val totalTimeOfSession: Double? = null
)
