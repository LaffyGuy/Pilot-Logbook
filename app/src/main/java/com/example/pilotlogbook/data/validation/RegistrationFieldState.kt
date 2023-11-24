package com.example.pilotlogbook.data.validation


data class RegistrationFieldState(
    val name: ValidationResult,
    val lastName: ValidationResult,
    val email: ValidationResult,
    val password: ValidationResult,
    val repeatPassword: ValidationResult
)

data class SignInFieldState(
    val email: ValidationResult,
    val password: ValidationResult
)
