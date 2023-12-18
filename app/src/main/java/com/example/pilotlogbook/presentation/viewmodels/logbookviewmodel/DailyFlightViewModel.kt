package com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel


import androidx.lifecycle.ViewModel
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyFlightViewModel @Inject constructor(private val dailyFlightRepository: DailyFlightRepository): ViewModel() {



//    init {
//        getDailyFlight()
//    }

//    private fun () {
//        _date.value = SelectDate()
//    }


    fun getDailyFlight() = dailyFlightRepository.getAllDaileFlightLog()


}