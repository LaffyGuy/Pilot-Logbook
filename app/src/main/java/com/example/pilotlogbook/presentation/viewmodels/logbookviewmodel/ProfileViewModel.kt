package com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.data.room.entities.tuples.AccountUpdateUsernameTuple
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.AppSettings.Companion.NO_ACCOUNT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val appSettings: AppSettings, private val accountRepository: AccountRepository): ViewModel() {


    fun findAccountById(id: Int) = accountRepository.findAccountById(id)

    fun  updateFirstNameOrLastName(accountUpdateUsernameTuple: AccountUpdateUsernameTuple) {
        viewModelScope.launch {
            accountRepository.updateFistNameOrLastName(accountUpdateUsernameTuple)
        }
    }

    fun logOut(){
        appSettings.setCurrentAccountId(NO_ACCOUNT_ID)
    }


}