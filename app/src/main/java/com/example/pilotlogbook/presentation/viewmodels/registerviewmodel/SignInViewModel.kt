package com.example.pilotlogbook.presentation.viewmodels.registerviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.data.validation.validatePasswordSignIn
import com.example.pilotlogbook.data.validation.validationEmailSignIn
import com.example.pilotlogbook.data.validation.SignInFieldState
import com.example.pilotlogbook.utils.AuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val accountRepository: AccountRepository): ViewModel() {

    private var _validation = MutableLiveData<SignInFieldState>()
    val validation: LiveData<SignInFieldState> = _validation

    private var _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id



    fun signIn(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            if(checkValidation(email, password)){
                try {
                   val id = accountRepository.findAccountByEmailAndPassword(email, password)
                   _id.postValue(id)
                }catch (e: AuthException){
                    Log.d("MyTag", "Error signIn - ${e.message.toString()}")
                }
            }else {
                val signInFieldState = SignInFieldState(validationEmailSignIn(email), validatePasswordSignIn(password))

                _validation.postValue(signInFieldState)
            }

        }
    }

    private fun checkValidation(email: String, password: String): Boolean {
        val emailValidation = validationEmailSignIn(email)
        val passwordValidation = validatePasswordSignIn(password)
        val shouldSignIn = emailValidation is ValidationResult.Success && passwordValidation is ValidationResult.Success

        return shouldSignIn
    }


}