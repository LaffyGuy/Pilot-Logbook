package com.example.pilotlogbook.domain.repositories

import com.example.pilotlogbook.data.room.entities.account.AccountEntity
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.data.validation.DailyFlightForm
import kotlinx.coroutines.flow.Flow

interface DailyFlightRepository {

    fun getAllDaileFlightLog(): Flow<List<DailyFlight>>

    suspend fun addDailyFlightLog(dailyFlightForm: DailyFlightForm)

}