package com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import com.example.pilotlogbook.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DailyFlightViewModel @Inject constructor(
    private val dailyFlightRepository: DailyFlightRepository): ViewModel() {


    val dailyFlightFlow: Flow<PagingData<DailyFlight>>
    private val sortTypeValue: MutableLiveData<SortType> = MutableLiveData()
    private val searchBy: MutableLiveData<String> = MutableLiveData("")

    init {
        dailyFlightFlow = combine(sortTypeValue.asFlow(), searchBy.asFlow()) { sort, search ->
            Pair(sort, search)
        }.flatMapLatest {
            dailyFlightRepository.getPagedDailyFlight(it.first, it.second)
        }.cachedIn(viewModelScope)
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

    fun refresh() {
        this.sortTypeValue.value = this.sortTypeValue.value
        this.searchBy.postValue(this.searchBy.value)
    }

    fun setSortType(sortType: SortType){
        sortTypeValue.postValue(sortType)
    }

}