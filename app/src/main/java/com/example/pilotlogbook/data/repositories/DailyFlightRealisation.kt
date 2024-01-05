package com.example.pilotlogbook.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pilotlogbook.data.room.db.PilotLogBookDataBase
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.data.validation.DailyFlightForm
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class DailyFlightRealisation(private val db: PilotLogBookDataBase): DailyFlightRepository {
    override fun getAllDaileFlightLog(): LiveData<List<DailyFlight>> {
        return db.getDailyFlightDao().getAllDaileFlightLog().map {entities ->
            entities.map { entity ->
                entity.toDailyFlight()
            }
        }
    }

    override suspend fun addDailyFlightLog(dailyFlightForm: DailyFlightForm) {
        dailyFlightForm.validate()
        delay(1000)
        val entity = DailyFlightEntity.fromDailyFlightForm(dailyFlightForm)
        db.getDailyFlightDao().addDailyFlightLog(entity)
    }

    override fun getPagedDailyFlight(searchBy: String): Flow<PagingData<DailyFlight>> {

        return Pager(
            config = PagingConfig(
                pageSize = 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {DailyFlightPagingSource(db.getDailyFlightDao(), 2, searchBy)}
        ).flow
    }

    override suspend fun updateTotalTimeOfFlight() {
        db.getDailyFlightDao().updateTotalTimeOfFlight()
    }
}


