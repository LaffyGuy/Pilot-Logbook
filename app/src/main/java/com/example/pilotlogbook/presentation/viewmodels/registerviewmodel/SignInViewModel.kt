package com.example.pilotlogbook.presentation.viewmodels.registerviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.R
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.domain.AuthEmailException
import com.example.pilotlogbook.domain.AuthPasswordException
import com.example.pilotlogbook.domain.EmptyFieldException
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.Field
import com.example.pilotlogbook.utils.Constance.NO_ERROR_MESSAGE
import com.example.pilotlogbook.utils.requireValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val accountRepository: AccountRepository, private val appSettings: AppSettings): ViewModel() {


    private var _id = MutableLiveData<Int>()
    val id: LiveData<Int> = _id

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun signIn(email: String, password: String){
        viewModelScope.launch {
            showProgressBar()
            try {
               val id = accountRepository.findAccountByEmailAndPassword(email, password)
                _id.postValue(id)
                appSettings.setCurrentAccountId(id)
            }catch (e: EmptyFieldException){
               processEmptyFieldsException(e)
            }catch (e: AuthEmailException){
                processAuthEmailException()
            }catch (e:AuthPasswordException){
                processAuthPasswordException()
            }finally {
                hideProgressBar()
            }
        }

    }

    private fun processEmptyFieldsException(e: EmptyFieldException){
        _state.value = when(e.field){
            Field.Email -> _state.requireValue().copy(emailErrorMessage = R.string.email_is_empty)
            Field.Password -> state.requireValue().copy(passwordErrorMessage = R.string.password_is_empty)
            else -> null
        }
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