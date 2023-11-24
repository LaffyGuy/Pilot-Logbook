package com.example.pilotlogbook.data.room.entities.dailyflight

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.data.room.entities.dailyflight.tuples.AircraftTuple
import com.example.pilotlogbook.data.room.entities.dailyflight.tuples.ArrivalTuple
import com.example.pilotlogbook.data.room.entities.dailyflight.tuples.DepartureTuple
import com.example.pilotlogbook.data.room.entities.dailyflight.tuples.LandingsTuple
import com.example.pilotlogbook.data.room.entities.dailyflight.tuples.OperationalConditionTimeTuple
import com.example.pilotlogbook.data.room.entities.dailyflight.tuples.PilotFunctionTimeTuple
import com.example.pilotlogbook.data.room.entities.dailyflight.tuples.SinglePilotTimeTuple
import com.example.pilotlogbook.data.room.entities.dailyflight.tuples.SyntheticTrainingDevicesSessionTuple

@Entity(tableName = "daily_flight_table")
data class DailyFlightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    @Embedded val departure: DepartureTuple,
    @Embedded val arrival: ArrivalTuple,
    @Embedded val aircraft: AircraftTuple,
    @Embedded val singlePilotTime: SinglePilotTimeTuple,
    val multiPilotTime: Double,
    val totalTimeOffFlight: Double,
    val picName: String,
    @Embedded val landings: LandingsTuple,
    @Embedded val operationalConditionTime: OperationalConditionTimeTuple,
    @Embedded val pilotFunctionTime: PilotFunctionTimeTuple,
    @Embedded val syntheticTrainingDevicesSession: SyntheticTrainingDevicesSessionTuple,
    val remarksAndEndorsements: String? = null
) {
    fun toDailyFlight(): DailyFlight = DailyFlight(
        id = id,
        date = date,
        departurePlace = departure.place,
        departureTime = departure.time,
        arrivalPlace = arrival.place,
        arrivalTime = arrival.time,
        aircraftModel = aircraft.model,
        aircraftRegistration = aircraft.registration,
        singlePilotTimeSe = singlePilotTime.se ?: "".toDouble(),
        singlePilotTimeMe = singlePilotTime.me ?: "".toDouble(),
        multiPilotTime = multiPilotTime,
        totalTimeOfFlight = totalTimeOffFlight,
        picName = picName,
        landingsDay = landings.day ?: "".toInt(),
        landingsNight = landings.night ?: "".toInt() ,
        operationalConditionTimeNight = operationalConditionTime.night ?: "".toDouble(),
        operationalConditionTimeIfr = operationalConditionTime.ifr ?: "".toDouble(),
        pilotFunctionTimePilotInComand = pilotFunctionTime.pilotInComand ?: "".toDouble(),
        pilotFunctionTimePilotCoPilot = pilotFunctionTime.coPilot ?: "".toDouble(),
        pilotFunctionTimePilotDual = pilotFunctionTime.dual ?: "".toDouble(),
        pilotFunctionTimePilotInstructor = pilotFunctionTime.instructor ?: "".toDouble(),
        syntheticTrainingDevicesSessionDate = syntheticTrainingDevicesSession.date ?: "",
        syntheticTrainingDevicesSessionType = syntheticTrainingDevicesSession.type ?: "",
        syntheticTrainingDevicesSessionTotalTimeOfSession = syntheticTrainingDevicesSession.totalTimeOfSession ?: "".toDouble(),
        remarksAndEndorsements = remarksAndEndorsements ?: ""
    )
}
