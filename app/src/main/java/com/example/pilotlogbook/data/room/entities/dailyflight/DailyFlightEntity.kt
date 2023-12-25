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
import com.example.pilotlogbook.data.validation.DailyFlightForm

@Entity(tableName = "daily_flight_table")
data class DailyFlightEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Long?,
    @Embedded val departure: DepartureTuple?,
    @Embedded val arrival: ArrivalTuple?,
    @Embedded val aircraft: AircraftTuple,
    @Embedded val singlePilotTime: SinglePilotTimeTuple?,
    val multiPilotTime: Double?,
    val totalTimeOffFlight: Long?,
    val picName: String?,
    @Embedded val landings: LandingsTuple?,
    @Embedded val operationalConditionTime: OperationalConditionTimeTuple?,
    @Embedded val pilotFunctionTime: PilotFunctionTimeTuple?,
    @Embedded val syntheticTrainingDevicesSession: SyntheticTrainingDevicesSessionTuple?,
    val remarksAndEndorsements: String?
) {
    fun toDailyFlight(): DailyFlight = DailyFlight(
        id = id,
        date = date,
        departurePlace = departure?.place,
        departureTime = departure?.time,
        arrivalPlace = arrival?.place,
        arrivalTime = arrival?.time,
        aircraftModel = aircraft.model,
        aircraftRegistration = aircraft.registration,
        singlePilotTimeSe = singlePilotTime?.se,
        singlePilotTimeMe = singlePilotTime?.me,
        multiPilotTime = multiPilotTime,
        totalTimeOfFlight = totalTimeOffFlight,
        picName = picName,
        landingsDay = landings?.day,
        landingsNight = landings?.night,
        operationalConditionTimeNight = operationalConditionTime?.night,
        operationalConditionTimeIfr = operationalConditionTime?.ifr,
        pilotFunctionTimePilotInComand = pilotFunctionTime?.pilotInComand,
        pilotFunctionTimePilotCoPilot = pilotFunctionTime?.coPilot,
        pilotFunctionTimePilotDual = pilotFunctionTime?.dual,
        pilotFunctionTimePilotInstructor = pilotFunctionTime?.instructor,
        syntheticTrainingDevicesSessionDate = syntheticTrainingDevicesSession?.date,
        syntheticTrainingDevicesSessionType = syntheticTrainingDevicesSession?.type,
        syntheticTrainingDevicesSessionTotalTimeOfSession = syntheticTrainingDevicesSession?.totalTimeOfSession,
        remarksAndEndorsements = remarksAndEndorsements
    )

    companion object {
        fun fromDailyFlightForm(dailyFlightForm: DailyFlightForm): DailyFlightEntity = DailyFlightEntity(
              id = 0,
              date = dailyFlightForm.date,
              departure = DepartureTuple(dailyFlightForm.departurePlace, dailyFlightForm.departureTime),
              arrival = ArrivalTuple(dailyFlightForm.arrivalPlace, dailyFlightForm.arrivalTime),
              aircraft = AircraftTuple(dailyFlightForm.aircraftModel, dailyFlightForm.aircraftRegistration),
              singlePilotTime = SinglePilotTimeTuple(dailyFlightForm.singlePilotTimeSe, dailyFlightForm.singlePilotTimeMe),
              multiPilotTime = dailyFlightForm.multiPilotTime,
              totalTimeOffFlight = dailyFlightForm.totalTimeOfFlight,
              picName = dailyFlightForm.picName,
              landings = LandingsTuple(dailyFlightForm.landingsDay, dailyFlightForm.landingsNight),
              operationalConditionTime = OperationalConditionTimeTuple(dailyFlightForm.operationalConditionTimeNight, dailyFlightForm.operationalConditionTimeIfr),
              pilotFunctionTime = PilotFunctionTimeTuple(
                  dailyFlightForm.pilotFunctionTimePilotInComand,
                  dailyFlightForm.pilotFunctionTimePilotCoPilot,
                  dailyFlightForm.pilotFunctionTimePilotDual,
                  dailyFlightForm.pilotFunctionTimePilotInstructor),
              syntheticTrainingDevicesSession = SyntheticTrainingDevicesSessionTuple(
                  dailyFlightForm.syntheticTrainingDevicesSessionDate,
                  dailyFlightForm.syntheticTrainingDevicesSessionType,
                  dailyFlightForm.syntheticTrainingDevicesSessionTotalTimeOfSession),
              remarksAndEndorsements = dailyFlightForm.remarksAndEndorsements
        )

    }
}
