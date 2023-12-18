package com.example.pilotlogbook.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.domain.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyFlightDao {

//    @Query("SELECT * FROM daily_flight_table")
//    fun getAllDaileFlightLog(): Flow<List<DailyFlightEntity>>

    @Query("SELECT * FROM daily_flight_table")
    fun getAllDaileFlightLog(): LiveData<List<DailyFlightEntity>>

    @Insert
    suspend fun addDailyFlightLog(dailyFlightEntity: DailyFlightEntity)

}