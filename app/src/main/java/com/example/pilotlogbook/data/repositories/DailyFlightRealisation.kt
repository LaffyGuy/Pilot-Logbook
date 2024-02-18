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
import com.example.pilotlogbook.utils.SortType
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
        val entity = DailyFlightEntity.fromDailyFlightForm(dailyFlightForm)
        db.getDailyFlightDao().addDailyFlightLog(entity)
        db.getDailyFlightDao().updateTotalTimeOfFlight()
    }

    override fun getPagedDailyFlight(sortType: SortType, searchBy: String): Flow<PagingData<DailyFlight>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {DailyFlightPagingSource(db.getDailyFlightDao(), db.getSortDailyFlightDao(), sortType, 10, searchBy)}
        ).flow
    }

    override suspend fun updateTotalTimeOfFlight() {
        db.getDailyFlightDao().updateTotalTimeOfFlight()
    }




}


