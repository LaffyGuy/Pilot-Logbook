package com.example.pilotlogbook.domain.repositories

import com.example.pilotlogbook.data.room.entities.account.AccountEntity
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import kotlinx.coroutines.flow.Flow

interface DailyFlightRepository {

    fun getAllDaileFlightLog(): Flow<List<DailyFlight>>

    suspend fun addDailyFlightLog(dailyFlightEntity: DailyFlightEntity)
//
//    suspend fun createAccount(accountEntity: AccountEntity)


}