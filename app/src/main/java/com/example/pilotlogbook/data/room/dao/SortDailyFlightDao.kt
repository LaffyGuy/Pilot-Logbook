package com.example.pilotlogbook.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity

@Dao
interface SortDailyFlightDao {

    @Query("SELECT * FROM daily_flight_table " +
            "WHERE :searchBy = '' OR departure_place LIKE '%' || :searchBy || '%' " +
            "ORDER BY departure_place DESC " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getDailyFlightByDateDesc(limit: Int, offset: Int, searchBy: String): List<DailyFlightEntity>

    @Query("SELECT * FROM daily_flight_table " +
            "WHERE :searchBy = '' OR departure_place LIKE '%' || :searchBy || '%' " +
            "ORDER BY departure_place ASC " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getDailyFlightByDateAsc(limit: Int, offset: Int, searchBy: String): List<DailyFlightEntity>

    @Query("SELECT * FROM daily_flight_table "+
            "WHERE :searchBy = '' OR departure_place LIKE '%' || :searchBy || '%' " +
            "ORDER BY departure_place DESC " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getDailyFlightByTotalTimeOfFlightDesc(limit: Int, offset: Int, searchBy: String): List<DailyFlightEntity>

    @Query("SELECT * FROM daily_flight_table " +
            "WHERE :searchBy = '' OR departure_place LIKE '%' || :searchBy || '%' " +
            "ORDER BY totalTimeOffFlight ASC " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getDailyFlightByTotalTimeOfFlightAsc(limit: Int, offset: Int, searchBy: String): List<DailyFlightEntity>
}