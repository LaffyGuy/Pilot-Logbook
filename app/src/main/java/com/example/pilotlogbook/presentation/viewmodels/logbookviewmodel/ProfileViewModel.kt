package com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel

import androidx.lifecycle.ViewModel
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.domain.settings.AppSettings
import com.example.pilotlogbook.domain.settings.AppSettings.Companion.NO_ACCOUNT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val appSettings: AppSettings, private val accountRepository: AccountRepository): ViewModel() {


    fun findAccountById(id: Int) = accountRepository.findAccountById(id)


    fun logOut(){
        appSettings.setCurrentAccountId(NO_ACCOUNT_ID)
    }


}