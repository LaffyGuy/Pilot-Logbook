package com.example.pilotlogbook.domain.entities

data class Account(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val createAt: Long,
    val totalDailyFlightTime: Long?,
    val imagePath: String?
)
