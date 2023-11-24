package com.example.pilotlogbook.data.validation


sealed class ValidationResult {
    object Success: ValidationResult()
    data class Failed(val error: String): ValidationResult()
}


