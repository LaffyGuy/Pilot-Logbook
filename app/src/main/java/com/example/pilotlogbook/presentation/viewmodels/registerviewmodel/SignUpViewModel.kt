package com.example.pilotlogbook.presentation.viewmodels.registerviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.R
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.data.validation.validationEmail
import com.example.pilotlogbook.data.validation.validationLastName
import com.example.pilotlogbook.data.validation.validationName
import com.example.pilotlogbook.data.validation.validationPassword
import com.example.pilotlogbook.data.validation.validationRepeatPassword
import com.example.pilotlogbook.data.validation.RegistrationFieldState
import com.example.pilotlogbook.data.validation.SignUp
import com.example.pilotlogbook.domain.AccountAlreadyExistException
import com.example.pilotlogbook.utils.Constance.NO_ERROR_MESSAGE
import com.example.pilotlogbook.utils.requireValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            withContext(Dispatchers.Main){
                showProgress()
            }
            try {
                if(checkValidation(signUp)){
                    accountRepository.signUp(signUp)
                    val registerFieldState = RegistrationFieldState(
                        validationName(signUp.firstName),
                        validationLastName(signUp.lastName),
                        validationEmail(signUp.email),
                        validationPassword(signUp.password),
                        validationRepeatPassword(signUp.password, signUp.repeatPassword)
                    )
                    _validation.postValue(registerFieldState)
                }else {
                    val registerFieldState = RegistrationFieldState(
                        validationName(signUp.firstName),
                        validationLastName(signUp.lastName),
                        validationEmail(signUp.email),
                        validationPassword(signUp.password),
                        validationRepeatPassword(signUp.password, signUp.repeatPassword)
                    )
                    _validation.postValue(registerFieldState)
                }
            }catch (e: AccountAlreadyExistException){
                 processAccountAlreadyExistException()
            }finally {
                withContext(Dispatchers.Main){
                    hideProgress()
                }
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
        val emailErrorMessage: Int = NO_ERROR_MESSAGE,
        val signUpProgress: Boolean = false
    ){
        val showProgress: Boolean = signUpProgress
    }

    private fun processAccountAlreadyExistException(){
        _state.value = _state.requireValue().copy(emailErrorMessage = R.string.account_already_exist)
    }

    private fun showProgress(){
        _state.value = State(signUpProgress = true)
    }
    private fun hideProgress(){
        _state.value = _state.requireValue().copy(signUpProgress = false)
    }

}