package com.example.pilotlogbook.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.domain.settings.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogBookViewModel @Inject constructor(private val accountsRepository: AccountRepository, private val appSettings: AppSettings): ViewModel() {

    fun getUserNameById() = accountsRepository.findAccountById(appSettings.getCurrentAccountId())

    fun logOut(){
        viewModelScope.launch(Dispatchers.IO) {
            accountsRepository.logOut()
        }
    }

}