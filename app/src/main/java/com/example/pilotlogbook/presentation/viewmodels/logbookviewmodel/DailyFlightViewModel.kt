package com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DailyFlightViewModel @Inject constructor(private val dailyFlightRepository: DailyFlightRepository): ViewModel() {

    val dailyFlightFlow: Flow<PagingData<DailyFlight>>

    private val searchBy = MutableLiveData("")


    init {
        dailyFlightFlow = searchBy.asFlow().debounce(500).flatMapLatest {
            dailyFlightRepository.getPagedDailyFlight(it)
        }
    }

    fun setSearchBy(value: String) {
        if(this.searchBy.value == value) return
        this.searchBy.value = value
    }

    fun refresh(){
        this.searchBy.postValue(this.searchBy.value)
    }

}