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
import com.example.pilotlogbook.domain.EmailPatternException
import com.example.pilotlogbook.domain.EmptyFieldException
import com.example.pilotlogbook.domain.PasswordMismatchException
import com.example.pilotlogbook.utils.Constance.NO_ERROR_MESSAGE
import com.example.pilotlogbook.utils.requireValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val accountRepository: AccountRepository): ViewModel() {

    private var _validation = MutableLiveData<RegistrationFieldState>()
    val validation: LiveData<RegistrationFieldState> = _validation


    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean> = _navigate


    fun findAccountByEmail(email: String){

    }

//    fun createAccount(signUp: SignUp){
//        viewModelScope.launch {
//              showProgress()
//            try {
//                if(checkValidation(signUp)){
//                    accountRepository.signUp(signUp)
//                    val registerFieldState = RegistrationFieldState(
//                        validationName(signUp.firstName),
//                        validationLastName(signUp.lastName),
//                        validationEmail(signUp.email),
//                        validationPassword(signUp.password),
//                        validationRepeatPassword(signUp.password, signUp.repeatPassword)
//                    )
//                    _validation.postValue(registerFieldState)
//                }else {
//                    val registerFieldState = RegistrationFieldState(
//                        validationName(signUp.firstName),
//                        validationLastName(signUp.lastName),
//                        validationEmail(signUp.email),
//                        validationPassword(signUp.password),
//                        validationRepeatPassword(signUp.password, signUp.repeatPassword)
//                    )
//                    _validation.postValue(registerFieldState)
//                }
//            }catch (e: AccountAlreadyExistException){
//                 processAccountAlreadyExistException()
//            }finally {
//                hideProgress()
//            }
//
//        }
//
//    }

//    fun createAccount(signUp: SignUp) {
//        viewModelScope.launch {
//            if (checkValidation(signUp)) {
//                showProgress()
//                try {
//                    accountRepository.signUp(signUp)
//                    val registerFieldState = RegistrationFieldState(
//                        validationName(signUp.firstName),
//                        validationLastName(signUp.lastName),
//                        validationEmail(signUp.email),
//                        validationPassword(signUp.password),
//                        validationRepeatPassword(signUp.password, signUp.repeatPassword)
//                    )
//                    _validation.postValue(registerFieldState)
//                    Log.d("MyTag11", "Try")
//
//                } catch (e: AccountAlreadyExistException) {
//                    processAccountAlreadyExistException()
//                    Log.d("MyTag11", "Catch")
//                } finally {
//                    hideProgress()
//                }
//
//            } else {
//                val registerFieldState = RegistrationFieldState(
//                    validationName(signUp.firstName),
//                    validationLastName(signUp.lastName),
//                    validationEmail(signUp.email),
//                    validationPassword(signUp.password),
//                    validationRepeatPassword(signUp.password, signUp.repeatPassword)
//                )
//                _validation.postValue(registerFieldState)
//            }
//        }
//    }

    fun createAccount(signUp: SignUp){
        viewModelScope.launch {
            showProgress()
            try {
                accountRepository.signUp(signUp)
                _navigate.value = true
            }catch (e: EmptyFieldException){
                Log.d("MyTag12", "Catch EmptyFieldException")
                processEmptyFieldsException(e)
            }catch (e: EmailPatternException){
                processEmailPatternsException()
            }catch (e: PasswordMismatchException){
                Log.d("MyTag12", "PasswordMismatchException")
                processPasswordMismatchException()
            }catch (e: AccountAlreadyExistException){
                Log.d("MyTag12", "PasswordMismatchException")
                processAccountAlreadyExistException()
            }finally {
                hideProgress()
            }
        }
    }

    private fun checkValidation(signUp: SignUp): Boolean {
        val nameValidation = validationName(signUp.firstName)
        val lastNameValidation = validationLastName(signUp.lastName)
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

    private fun processEmptyFieldsException(e: EmptyFieldException){
//        _state.value = when(e.field){
//            Field.FirstName -> _state.requireValue().copy(firstNameErrorMessage = R.string.name_is_empty)
//            Field.LastName -> _state.requireValue().copy(lastNameErrorMessage = R.string.last_name_is_empty)
//            Field.Email -> _state.requireValue().copy(emailErrorMessage = R.string.email_is_empty)
//            Field.Password -> _state.requireValue().copy(passwordErrorMessage = R.string.password_is_empty)
//            Field.RepeatPassword -> _state.requireValue().copy(repeatPasswordErrorMessage = R.string.repeat_password_is_empty)
//
//        }
        _state.value = _state.requireValue().copy(firstNameErrorMessage = R.string.name_is_empty, lastNameErrorMessage = R.string.last_name_is_empty,
        emailErrorMessage = R.string.email_is_empty, passwordErrorMessage = R.string.password_is_empty, repeatPasswordErrorMessage = R.string.repeat_password_mismatch)

    }

    private fun processEmailPatternsException(){
        _state.value = _state.requireValue().copy(emailErrorMessage = R.string.email_patterns_error)
    }

    private fun processPasswordMismatchException(){
        _state.value = _state.requireValue().copy(repeatPasswordErrorMessage = R.string.repeat_password_mismatch)
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

    data class State(
        val firstNameErrorMessage: Int = NO_ERROR_MESSAGE,
        val lastNameErrorMessage: Int = NO_ERROR_MESSAGE,
        val emailErrorMessage: Int = NO_ERROR_MESSAGE,
        val passwordErrorMessage: Int = NO_ERROR_MESSAGE,
        val repeatPasswordErrorMessage: Int = NO_ERROR_MESSAGE,
        val signUpProgress: Boolean = false
    ){
        val showProgress: Boolean = signUpProgress
    }

}