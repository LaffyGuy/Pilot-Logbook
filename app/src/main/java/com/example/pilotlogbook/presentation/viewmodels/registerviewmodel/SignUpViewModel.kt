package com.example.pilotlogbook.presentation.viewmodels.registerviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.R
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.data.validation.SignUp
import com.example.pilotlogbook.domain.AccountAlreadyExistException
import com.example.pilotlogbook.domain.EmailPatternException
import com.example.pilotlogbook.domain.EmptyFieldException
import com.example.pilotlogbook.domain.PasswordLengthException
import com.example.pilotlogbook.domain.PasswordMismatchException
import com.example.pilotlogbook.domain.settings.Field
import com.example.pilotlogbook.utils.Constance.NO_ERROR_MESSAGE
import com.example.pilotlogbook.utils.requireValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val accountRepository: AccountRepository): ViewModel() {


    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean> = _navigate


    fun findAccountByEmail(email: String){

    }

    fun createAccount(signUp: SignUp){
        viewModelScope.launch {
            showProgress()
            try {
                accountRepository.signUp(signUp)
                _navigate.value = true
            }catch (e: EmptyFieldException){
                processEmptyFieldsException(e)
            }catch (e: EmailPatternException){
                processEmailPatternsException()
            }catch (e: PasswordMismatchException){
                processPasswordMismatchException()
            }catch (e: AccountAlreadyExistException){
                processAccountAlreadyExistException()
            }catch (e: PasswordLengthException){
                processPasswordLengthException()
            } finally {
                hideProgress()
            }
        }
    }


    private fun processEmptyFieldsException(e: EmptyFieldException){
        _state.value = when(e.field){
            Field.FirstName -> _state.requireValue().copy(firstNameErrorMessage = R.string.name_is_empty)
            Field.LastName -> _state.requireValue().copy(lastNameErrorMessage = R.string.last_name_is_empty)
            Field.Email -> _state.requireValue().copy(emailErrorMessage = R.string.email_is_empty)
            Field.Password -> _state.requireValue().copy(passwordErrorMessage = R.string.password_is_empty)
            Field.RepeatPassword -> _state.requireValue().copy(repeatPasswordErrorMessage = R.string.repeat_password_is_empty)
        }
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

    private fun processPasswordLengthException(){
        _state.value = _state.requireValue().copy(passwordErrorMessage = R.string.password_length_error)
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