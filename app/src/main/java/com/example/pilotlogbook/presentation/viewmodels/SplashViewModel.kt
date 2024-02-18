package com.example.pilotlogbook.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.domain.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val accountRepository: AccountRepository): ViewModel() {

    private val _isReady = MutableStateFlow(true)
    val isReady = _isReady.asStateFlow()

    private val _isSignedIn = MutableLiveData<Boolean>()
    val isSignedIn: LiveData<Boolean> = _isSignedIn


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isSignedIn.postValue(isSignedIn())
        }
    }

    private suspend fun isSignedIn(): Boolean {
        delay(2000)
        _isReady.value = false
        return accountRepository.isSignedIn()
    }
}