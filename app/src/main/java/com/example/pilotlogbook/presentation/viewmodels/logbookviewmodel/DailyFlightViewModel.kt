package com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import com.example.pilotlogbook.domain.settings.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyFlightViewModel @Inject constructor(private val dailyFlightRepository: DailyFlightRepository): ViewModel() {


    fun getAllDailyFlightLog() = dailyFlightRepository.getAllDaileFlightLog()

    fun addDailyFlight(dailyFlightEntity: DailyFlightEntity){
        viewModelScope.launch(Dispatchers.IO) {
            dailyFlightRepository.addDailyFlightLog(dailyFlightEntity)
        }
    }




}