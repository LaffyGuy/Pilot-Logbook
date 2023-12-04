package com.example.pilotlogbook.data.validation

data class AddDailyFlightFieldState(
    val date: ValidationResult,
    val departurePlace: ValidationResult,
    val departureTime: ValidationResult,
    val arrivalPlace: ValidationResult,
    val arrivalTime : ValidationResult,
    val model: ValidationResult,
    val registration: ValidationResult
)

