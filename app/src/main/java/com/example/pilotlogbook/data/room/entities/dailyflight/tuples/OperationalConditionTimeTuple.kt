package com.example.pilotlogbook.data.room.entities.dailyflight.tuples

import androidx.room.ColumnInfo

data class OperationalConditionTimeTuple(
    @ColumnInfo(name = "operation_night")val night: Double?,
    val ifr: Double?
)
