package com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilotlogbook.R
import com.example.pilotlogbook.data.validation.DailyFlightForm
import com.example.pilotlogbook.domain.EmptyAddFlightFieldException
import com.example.pilotlogbook.domain.NotValidInputTimeException
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import com.example.pilotlogbook.domain.settings.AddDailyFlightField
import com.example.pilotlogbook.utils.Constance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDailyFlightViewModel @Inject constructor(private val dailyFlightRepository: DailyFlightRepository): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _navigate = MutableLiveData<Boolean>()
    val navigate: LiveData<Boolean> = _navigate


    private val _date = MutableLiveData<Long>()
    val date: LiveData<Long> = _date

    private val _departureTime = MutableLiveData<Long>()
    val departureTime: LiveData<Long> = _departureTime

    private val _arrivalTime = MutableLiveData<Long>()
    val arrivalTime: LiveData<Long> = _arrivalTime

    fun addDailyFlight(dailyFlightForm: DailyFlightForm){
        viewModelScope.launch {
            processShowProgressBar()
            try {
                dailyFlightRepository.addDailyFlightLog(dailyFlightForm)
                _navigate.value = true
                Log.d("MyTag30", "Try")
            }catch (e: EmptyAddFlightFieldException){
                processEmptyFieldsException(e)
                Log.d("MyTag30", "Catch")
            }catch (e: NotValidInputTimeException){
                processNotValidInputType(e)
            }finally {
                processHideProgressBar()
            }
        }
    }

    private fun processEmptyFieldsException(e: EmptyAddFlightFieldException){
        _state.value = when(e.field){
            AddDailyFlightField.Date -> _state.value?.copy(dateErrorMessage = R.string.date_is_empty)
            AddDailyFlightField.DeparturePlace -> state.value?.copy(departurePlaceErrorMessage = R.string.departure_place_is_empty)
            AddDailyFlightField.DepartureTime -> _state.value?.copy(departureTimeErrorMessage = R.string.departure_time_is_empty)
            AddDailyFlightField.ArrivalPlace -> _state.value?.copy(arrivalPlaceErrorMessage = R.string.arrival_place_is_empty)
            AddDailyFlightField.ArrivalTime -> _state.value?.copy(arrivalTimeErrorMessage = R.string.arrival_time_is_empty)
            AddDailyFlightField.Model -> state.value?.copy(modelErrorMessage = R.string.model_is_empty)
            AddDailyFlightField.Registration -> _state.value?.copy(registrationErrorMessage = R.string.registration_is_empty)
            AddDailyFlightField.TotalTimeOfFlight -> _state.value?.copy(totalTimeOfFlightErrorMessage = R.string.total_time_of_flight_is_empty)
        }
        Log.d("MyTag30", "State value - ${_state.value}")
    }

    private fun processShowProgressBar(){
        _state.value = State(addProgress = true)
    }

    private fun processHideProgressBar(){
        _state.value = _state.value?.copy(addProgress = false)
    }

    private fun processNotValidInputType(e: NotValidInputTimeException){
        _state.value = _state.value?.copy(departureTimeErrorMessage = R.string.not_valid_departure_time_type)
    }

    data class State(
        val dateErrorMessage: Int = Constance.NO_ERROR_MESSAGE,
        val departurePlaceErrorMessage: Int = Constance.NO_ERROR_MESSAGE,
        val departureTimeErrorMessage: Int = Constance.NO_ERROR_MESSAGE,
        val arrivalPlaceErrorMessage: Int = Constance.NO_ERROR_MESSAGE,
        val arrivalTimeErrorMessage: Int = Constance.NO_ERROR_MESSAGE,
        val modelErrorMessage: Int = Constance.NO_ERROR_MESSAGE,
        val registrationErrorMessage: Int = Constance.NO_ERROR_MESSAGE,
        val totalTimeOfFlightErrorMessage: Int = Constance.NO_ERROR_MESSAGE,
        val addProgress: Boolean = false
    ){
        val showProgress = addProgress
    }

    fun setDate(date: Long){
        _date.value = date
    }

    fun setDepartureTime(departureTime: Long){
        _departureTime.value = departureTime
    }

    fun setArrivalTime(arrivalTime: Long){
        _arrivalTime.value = arrivalTime
    }

}