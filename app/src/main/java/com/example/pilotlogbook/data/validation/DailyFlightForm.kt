package com.example.pilotlogbook.data.validation

import com.example.pilotlogbook.domain.EmptyAddFlightFieldException
import com.example.pilotlogbook.domain.settings.AddDailyFlightField

data class DailyFlightForm(
    val date: Long?,
    val departurePlace: String,
    val departureTime: Long?,
    val arrivalPlace: String,
    val arrivalTime: Long?,
    val aircraftModel: String,
    val aircraftRegistration: String,
    val singlePilotTimeSe: Long?,
    val singlePilotTimeMe: Long?,
    val multiPilotTime: Long?,
    val totalTimeOfFlight: Long?,
    val picName: String?,
    val landingsDay: Int?,
    val landingsNight: Int?,
    val operationalConditionTimeNight: Long?,
    val operationalConditionTimeIfr: Long?,
    val pilotFunctionTimePilotInComand: Long?,
    val pilotFunctionTimePilotCoPilot: Long?,
    val pilotFunctionTimePilotDual: Long?,
    val pilotFunctionTimePilotInstructor: Long?,
    val syntheticTrainingDevicesSessionDate: String?,
    val syntheticTrainingDevicesSessionType: String?,
    val syntheticTrainingDevicesSessionTotalTimeOfSession: Long?,
    val remarksAndEndorsements: String?
){

    fun validate(){
        if(date == null) throw EmptyAddFlightFieldException(AddDailyFlightField.Date)
        if(departurePlace.isBlank()) throw EmptyAddFlightFieldException(AddDailyFlightField.DeparturePlace)
        if(departureTime.toString().isBlank() || departureTime == null) throw EmptyAddFlightFieldException(AddDailyFlightField.DepartureTime)
//        if(!Pattern.compile("([01]?[0-9]|2[0-3]).([0-5][0-9])").matcher(departureTime.toString()).matches()) throw NotValidInputTimeException(AddDailyFlightField.DepartureTime)
        if(arrivalPlace.isBlank()) throw EmptyAddFlightFieldException(AddDailyFlightField.ArrivalPlace)
        if(arrivalTime.toString().isBlank() || arrivalTime == null) throw EmptyAddFlightFieldException(AddDailyFlightField.ArrivalTime)
        if(aircraftModel.isBlank()) throw EmptyAddFlightFieldException(AddDailyFlightField.Model)
        if(aircraftRegistration.isBlank()) throw EmptyAddFlightFieldException(AddDailyFlightField.Registration)
        if(totalTimeOfFlight.toString().isBlank() || totalTimeOfFlight == null || totalTimeOfFlight == 0L) throw EmptyAddFlightFieldException(AddDailyFlightField.TotalTimeOfFlight)
    }

}