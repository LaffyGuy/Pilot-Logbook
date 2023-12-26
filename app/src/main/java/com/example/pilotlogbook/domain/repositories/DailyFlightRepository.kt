package com.example.pilotlogbook.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.validation.DailyFlightForm
import kotlinx.coroutines.flow.Flow

interface DailyFlightRepository {

    fun getAllDaileFlightLog(): LiveData<List<DailyFlight>>

    suspend fun addDailyFlightLog(dailyFlightForm: DailyFlightForm)

    fun getPagedDailyFlight(searchBy : String): Flow<PagingData<DailyFlight>>

}