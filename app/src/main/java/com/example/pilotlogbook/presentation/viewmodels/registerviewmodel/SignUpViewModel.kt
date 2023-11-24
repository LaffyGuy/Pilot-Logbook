package com.example.pilotlogbook.presentation.viewmodels.registerviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.data.validation.validationEmail
import com.example.pilotlogbook.data.validation.validationLastName
import com.example.pilotlogbook.data.validation.validationName
import com.example.pilotlogbook.data.validation.validationPassword
import com.example.pilotlogbook.data.validation.validationRepeatPassword
import com.example.pilotlogbook.data.validation.RegistrationFieldState
import com.example.pilotlogbook.data.validation.SignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val accountRepository: AccountRepository): ViewModel() {

    private var _validation = MutableLiveData<RegistrationFieldState>()
    val validation: LiveData<RegistrationFieldState> = _validation


    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state


    fun findAccountByEmail(email: String){

    }

    fun createAccount(signUp: SignUp){
        viewModelScope.launch {
            if(checkValidation(signUp)){
                try {
                    accountRepository.signUp(signUp)
                    _state.postValue(State(true))
                }catch (e: Exception){
                    Log.d("MyTag", "Error signUp - ${e.message.toString()}")
                }
            }else {
                val registerFieldState = RegistrationFieldState(
                    validationName(signUp.firstName),
                    validationLastName(signUp.lastName),
                    validationEmail(signUp.email),
                    validationPassword(signUp.password),
                    validationRepeatPassword(signUp.password, signUp.repeatPassword)
                )
                _state.postValue(State(false))
                _validation.postValue(registerFieldState)
            }
        }

    }

    private fun checkValidation(signUp: SignUp): Boolean {
        val nameValidation = validationName(signUp.firstName)
        val lastNameValidation = validationName(signUp.lastName)
        val emailValidation = validationEmail(signUp.email)
        val passwordValidation = validationPassword(signUp.password)
        val repeatPasswordValidation = validationRepeatPassword(signUp.password, signUp.repeatPassword)
        val shouldRegister = nameValidation is ValidationResult.Success
                && lastNameValidation is ValidationResult.Success
                && emailValidation is ValidationResult.Success
                && passwordValidation is ValidationResult.Success
                && repeatPasswordValidation is ValidationResult.Success

        return shouldRegister
    }

    data class State(
        val success: Boolean
    )

}