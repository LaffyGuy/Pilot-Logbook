package com.example.pilotlogbook.data.validation

import android.util.Patterns

fun validationName(name: String): ValidationResult {
        if(name.isBlank()){
            return ValidationResult.Failed("The name can`t be blank")
        }
        val containsLetters = name.any {it.isUpperCase()}
        if(!containsLetters){
            return ValidationResult.Failed("The name must start with a capital letter")
        }
      return ValidationResult.Success
    }

fun validationLastName(lastName: String): ValidationResult {
        if(lastName.isBlank()){
            return ValidationResult.Failed("The last name can`t be blank")
        }
        val containsLetters = lastName.any {it.isUpperCase()}
        if(!containsLetters){
            return ValidationResult.Failed("The last name must start with a capital letter")
        }
        return ValidationResult.Success
}

fun validationEmail(email: String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult.Failed("The email can`t be blank")
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult.Failed("That`s not valid email")
        }
        return ValidationResult.Success
}

fun validationPassword(password: String): ValidationResult {
        if(password.isBlank()){
            return ValidationResult.Failed("The name can`t be blank")
        }
        val containsLetters = password.any {it.isLetter()} && password.any { it.isDigit() }
        if(!containsLetters){
            return ValidationResult.Failed("The password needs to contain at least one letter and digit")
        }
        if(password.length > 8){
            return ValidationResult.Failed("The password needs to consist at least 8 characters")
        }
        return ValidationResult.Success
}

fun validationRepeatPassword(password: String, repeatPassword: String): ValidationResult {
        if(repeatPassword.isBlank()){
            return ValidationResult.Failed("The repeatedPassword can`t be blank")
        }
        if(password != repeatPassword){
            return ValidationResult.Failed("The passwords must be the same")
        }
        val containsLetters = repeatPassword.any {it.isUpperCase()}
        if(!containsLetters){
            return ValidationResult.Failed("The password needs to contain at least one letter and digit")
        }
        if(password.length < 8){
            return ValidationResult.Failed("The password needs to consist at least 8 characters")
        }
        return ValidationResult.Success
}

fun validationEmailSignIn(email: String): ValidationResult {
        if(email.isBlank()){
             return ValidationResult.Failed("The email can`t be blank")
        }
        return ValidationResult.Success
}

fun validatePasswordSignIn(password: String): ValidationResult {
        if(password.isBlank()){
            return ValidationResult.Failed("The name can`t be blank")
        }
        return ValidationResult.Success
}


