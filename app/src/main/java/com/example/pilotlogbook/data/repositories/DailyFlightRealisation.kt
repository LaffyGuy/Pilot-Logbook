package com.example.pilotlogbook.data.repositories

import android.util.Log
import com.example.pilotlogbook.data.room.db.PilotLogBookDataBase
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class DailyFlightRealisation(private val db: PilotLogBookDataBase): DailyFlightRepository {

    override fun getAllDaileFlightLog(): Flow<List<DailyFlight>> {
        return db.getDailyFlightDao().getAllDaileFlightLog().map { entities ->
            entities.map { entity ->
                entity.toDailyFlight()
            }
        }
    }

    override suspend fun addDailyFlightLog(dailyFlightEntity: DailyFlightEntity) {
        try {
            db.getDailyFlightDao().addDailyFlightLog(dailyFlightEntity)
        }catch (e: Exception){
            Log.d("MyTag", "Error dailyFlight - ${e.message.toString()}")
        }
    }
}