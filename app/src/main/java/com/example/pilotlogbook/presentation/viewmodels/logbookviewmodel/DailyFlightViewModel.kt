package com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.data.validation.AddDailyFlightFieldState
import com.example.pilotlogbook.data.validation.DailyFlightForm
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.data.validation.validateArrivalPlace
import com.example.pilotlogbook.data.validation.validateArrivalTime
import com.example.pilotlogbook.data.validation.validateDate
import com.example.pilotlogbook.data.validation.validateDeparturePlace
import com.example.pilotlogbook.data.validation.validateDepartureTime
import com.example.pilotlogbook.data.validation.validateModel
import com.example.pilotlogbook.data.validation.validateRegistration
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.domain.repositories.AccountRepository
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import com.example.pilotlogbook.domain.settings.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyFlightViewModel @Inject constructor(private val dailyFlightRepository: DailyFlightRepository): ViewModel() {

    private val _validation = MutableLiveData<AddDailyFlightFieldState>()
    val validation: LiveData<AddDailyFlightFieldState> = _validation

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun getAllDailyFlightLog() = dailyFlightRepository.getAllDaileFlightLog()

    fun addDailyFlight(dailyFlightForm: DailyFlightForm){
        viewModelScope.launch(Dispatchers.IO) {
            if(checkValidation(dailyFlightForm)){
                dailyFlightRepository.addDailyFlightLog(dailyFlightForm)
                _state.postValue(true)
            }else {
                val addDailyFlightFieldState = AddDailyFlightFieldState(
                    validateDate(dailyFlightForm.date),
                    validateDeparturePlace(dailyFlightForm.departurePlace),
                    validateDepartureTime(dailyFlightForm.departureTime.toString()),
                    validateArrivalPlace(dailyFlightForm.arrivalPlace),
                    validateArrivalTime(dailyFlightForm.arrivalTime.toString()),
                    validateModel(dailyFlightForm.aircraftModel),
                    validateRegistration(dailyFlightForm.aircraftRegistration)
                )
                _validation.postValue(addDailyFlightFieldState)
                _state.postValue(false)
            }

        }
    }

    private fun checkValidation(dailyFlightForm: DailyFlightForm): Boolean {
        val validateDate = validateDate(dailyFlightForm.date)
        val validateDateDeparturePlace = validateDeparturePlace(dailyFlightForm.departurePlace)
        val validateDepartureTime = validateDepartureTime(dailyFlightForm.departureTime.toString())
        val validateArrivalPlace = validateArrivalPlace(dailyFlightForm.arrivalPlace)
        val validateArrivalTime = validateArrivalTime(dailyFlightForm.arrivalTime.toString())
        val validateModel = validateModel(dailyFlightForm.aircraftModel)
        val validateRegistration = validateRegistration(dailyFlightForm.aircraftRegistration)
        val shouldAddDailyFlight = validateDate is ValidationResult.Success
                && validateDateDeparturePlace is ValidationResult.Success
                && validateDepartureTime is ValidationResult.Success
                && validateArrivalPlace is ValidationResult.Success
                && validateArrivalTime is ValidationResult.Success
                && validateModel is ValidationResult.Success
                && validateRegistration is ValidationResult.Success
        return shouldAddDailyFlight
    }

}