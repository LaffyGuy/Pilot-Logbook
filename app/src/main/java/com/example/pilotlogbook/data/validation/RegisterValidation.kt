package com.example.pilotlogbook.data.validation

import android.util.Patterns
import com.example.pilotlogbook.domain.EmailPatternException
import com.example.pilotlogbook.domain.EmptyFieldException
import com.example.pilotlogbook.domain.PasswordLengthException
import com.example.pilotlogbook.domain.PasswordMismatchException
import com.example.pilotlogbook.domain.settings.Field

data class SignUp(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val repeatPassword: String,
){

    fun validate(){
        if(firstName.isBlank()) throw EmptyFieldException(Field.FirstName)
        if(lastName.isBlank()) throw EmptyFieldException(Field.LastName)
        if(email.isBlank()) throw EmptyFieldException(Field.Email)
        if(password.isBlank()) throw EmptyFieldException(Field.Password)
        if(repeatPassword.isBlank()) throw EmptyFieldException(Field.RepeatPassword)
        if(repeatPassword != password) throw PasswordMismatchException()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) throw EmailPatternException()
        if(password.length < 8) throw PasswordLengthException()
    }

}

