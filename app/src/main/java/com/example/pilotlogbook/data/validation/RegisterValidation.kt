package com.example.pilotlogbook.data.validation

data class SignUp(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
)

