package com.example.pilotlogbook.presentation.viewmodels.registerviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.R
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.data.validation.validatePasswordSignIn
import com.example.pilotlogbook.data.validation.validationEmailSignIn
import com.example.pilotlogbook.data.validation.SignInFieldState
import com.example.pilotlogbook.domain.AuthEmailException
import com.example.pilotlogbook.domain.AuthPasswordException
import com.example.pilotlogbook.domain.EmptyFieldException
import com.example.pilotlogbook.domain.PasswordMismatchException
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.Field
import com.example.pilotlogbook.utils.Constance.NO_ERROR_MESSAGE
import com.example.pilotlogbook.utils.requireValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val accountRepository: AccountRepository, private val appSettings: AppSettings): ViewModel() {

    private var _validation = MutableLiveData<SignInFieldState>()
    val validation: LiveData<SignInFieldState> = _validation

    private var _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state



//    fun signIn(email: String, password: String){
//        viewModelScope.launch(Dispatchers.IO) {
//              showProgressBar()
//            try {
//                if(checkValidation(email, password)) {
//                    val id = accountRepository.findAccountByEmailAndPassword(email, password)
//                    _id.postValue(id)
//                    appSettings.setCurrentAccountId(id)
//                }else {
//                    val signInFieldState = SignInFieldState(validationEmailSignIn(email), validatePasswordSignIn(password))
//                    _validation.postValue(signInFieldState)
//                }
//            }catch (e: AuthEmailException){
//                    processAuthEmailException()
//            }catch (e: AuthPasswordException){
//                processAuthPasswordException()
//            } finally {
//                hideProgressBar()
//            }
//
//        }
//    }

//    fun signIn(email: String, password: String){
//        viewModelScope.launch(Dispatchers.IO) {
//            if(checkValidation(email, password)){
//                showProgressBar()
//                try {
//                    val id = accountRepository.findAccountByEmailAndPassword(email, password)
//                    _id.postValue(id)
//                    appSettings.setCurrentAccountId(id)
//                    Log.d("MyTag11", "Try")
//                }catch (e: AuthEmailException){
//                    processAuthEmailException()
//                    Log.d("MyTag11", "Catch Email")
//                }catch (e: AuthPasswordException){
//                    processAuthPasswordException()
//                    Log.d("MyTag11", "Catch Password")
//                }finally {
//                    hideProgressBar()
//                    Log.d("MyTag11", "Finally")
//                 }
//            }else {
//                val signInFieldState = SignInFieldState(validationEmailSignIn(email), validatePasswordSignIn(password))
//                _validation.postValue(signInFieldState)
//            }
//        }
//    }

//    private fun checkValidation(email: String, password: String): Boolean {
//        val emailValidation = validationEmailSignIn(email)
//        val passwordValidation = validatePasswordSignIn(password)
//        val shouldSignIn = emailValidation is ValidationResult.Success && passwordValidation is ValidationResult.Success
//
//        return shouldSignIn
//    }

    fun signIn(email: String, password: String){
        viewModelScope.launch {
            showProgressBar()
            try {
               val id = accountRepository.findAccountByEmailAndPassword(email, password)
                _id.postValue(id)
                appSettings.setCurrentAccountId(id)
            }catch (e: EmptyFieldException){
               processEmptyFieldsException()
            }catch (e: AuthEmailException){
                processAuthEmailException()
            }catch (e:AuthPasswordException){
                processAuthPasswordException()
            }finally {
                hideProgressBar()
            }
        }

    }

    private fun processEmptyFieldsException(){
        _state.value = _state.requireValue().copy(emailErrorMessage = R.string.email_is_empty, passwordErrorMessage = R.string.password_is_empty)
    }

    private fun processAuthEmailException(){
        _state.value = _state.requireValue().copy(emailErrorMessage = R.string.email_not_found)
    }

    private fun processAuthPasswordException(){
        _state.value = _state.requireValue().copy(passwordErrorMessage = R.string.password_not_found)
    }

    private fun showProgressBar(){
        _state.value = State(signInProgress = true)
    }

    private fun hideProgressBar(){
        _state.value = _state.requireValue().copy(signInProgress = false)
    }

    data class State(
        val emailErrorMessage: Int = NO_ERROR_MESSAGE,
        val passwordErrorMessage: Int = NO_ERROR_MESSAGE,
        val signInProgress: Boolean = false
    ){
        val showProgress: Boolean = signInProgress
    }



}