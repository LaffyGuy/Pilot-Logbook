package com.example.pilotlogbook.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.validation.DailyFlightForm
import com.example.pilotlogbook.domain.Result
import kotlinx.coroutines.flow.Flow

interface DailyFlightRepository {

//    fun getAllDaileFlightLog(): Flow<Result<List<DailyFlight>>>


    fun getAllDaileFlightLog(): LiveData<List<DailyFlight>>

    suspend fun addDailyFlightLog(dailyFlightForm: DailyFlightForm)

    fun getPagedDailyFlight(searchBy : String): Flow<PagingData<DailyFlight>>

    fun getDailyFlightTest(searchBy: String): LiveData<List<DailyFlight>>

}