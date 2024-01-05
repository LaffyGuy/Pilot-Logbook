package com.example.pilotlogbook.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity

@Dao
interface DailyFlightDao {

    @Query("SELECT * FROM daily_flight_table")
    fun getAllDaileFlightLog(): LiveData<List<DailyFlightEntity>>

    @Insert
    suspend fun addDailyFlightLog(dailyFlightEntity: DailyFlightEntity)

    @Query("SELECT * FROM daily_flight_table " +
            "WHERE :searchBy = '' OR departure_place LIKE '%' || :searchBy || '%' " +
            "ORDER BY departure_place " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getAllDailyFlight(limit: Int, offset: Int, searchBy: String = ""): List<DailyFlightEntity>

    @Query("UPDATE account_table SET totalDailyFlightTime = (SELECT SUM(daily_flight_table.totalTimeOffFlight) FROM daily_flight_table)")
    suspend fun updateTotalTimeOfFlight()


}