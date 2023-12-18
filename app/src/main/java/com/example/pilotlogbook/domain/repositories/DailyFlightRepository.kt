package com.example.pilotlogbook.domain.repositories

import androidx.lifecycle.LiveData
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.validation.DailyFlightForm
import com.example.pilotlogbook.domain.Result
import kotlinx.coroutines.flow.Flow

interface DailyFlightRepository {

//    fun getAllDaileFlightLog(): Flow<Result<List<DailyFlight>>>


    fun getAllDaileFlightLog(): LiveData<List<DailyFlight>>

    suspend fun addDailyFlightLog(dailyFlightForm: DailyFlightForm)

}