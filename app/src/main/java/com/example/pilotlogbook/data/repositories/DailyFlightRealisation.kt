package com.example.pilotlogbook.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.pilotlogbook.data.room.db.PilotLogBookDataBase
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.room.entities.dailyflight.DailyFlightEntity
import com.example.pilotlogbook.data.validation.DailyFlightForm
import com.example.pilotlogbook.domain.ErrorResult
import com.example.pilotlogbook.domain.LoadingResult
import com.example.pilotlogbook.domain.Result
import com.example.pilotlogbook.domain.SuccessResult
import com.example.pilotlogbook.domain.repositories.DailyFlightRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.Exception

class DailyFlightRealisation(private val db: PilotLogBookDataBase): DailyFlightRepository {
    override fun getAllDaileFlightLog(): LiveData<List<DailyFlight>> {
        return db.getDailyFlightDao().getAllDaileFlightLog().map {entities ->
            entities.map { entity ->
                entity.toDailyFlight()
            }
        }
    }

//    override fun getAllDaileFlightLog(): Flow<Result<List<DailyFlight>>> {
//        return db.getDailyFlightDao().getAllDaileFlightLog().map { entities ->
//            entities.map { entity ->
//                entity.toDailyFlight()
//            }
//        }
//    }

//    override fun getAllDaileFlightLog(): Flow<Result<List<DailyFlight>>> {
//        return db.getDailyFlightDao().getAllDaileFlightLog().map { entities ->
//            val data = entities.map { entity ->
//                entity.toDailyFlight()
//            }
//            SuccessResult(data)
//        }.catch {
//
//        }
//        }
//
//    }



    override suspend fun addDailyFlightLog(dailyFlightForm: DailyFlightForm) {
        dailyFlightForm.validate()
        delay(1000)
        val entity = DailyFlightEntity.fromDailyFlightForm(dailyFlightForm)
        db.getDailyFlightDao().addDailyFlightLog(entity)
    }
}


