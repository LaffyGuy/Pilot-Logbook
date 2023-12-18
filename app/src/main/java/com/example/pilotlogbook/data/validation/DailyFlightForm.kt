package com.example.pilotlogbook.data.validation

import com.example.pilotlogbook.domain.EmptyAddFlightFieldException
import com.example.pilotlogbook.domain.NotValidInputTimeException
import com.example.pilotlogbook.domain.settings.AddDailyFlightField
import java.util.regex.Pattern

data class DailyFlightForm(
    val date: Long?,
    val departurePlace: String,
    val departureTime: Int?,
    val arrivalPlace: String,
    val arrivalTime: Int?,
    val aircraftModel: String,
    val aircraftRegistration: String,
    val singlePilotTimeSe: Double?,
    val singlePilotTimeMe: Double?,
    val multiPilotTime: Double?,
    val totalTimeOfFlight: Int?,
    val picName: String?,
    val landingsDay: Int?,
    val landingsNight: Int?,
    val operationalConditionTimeNight: Double?,
    val operationalConditionTimeIfr: Double?,
    val pilotFunctionTimePilotInComand: Double?,
    val pilotFunctionTimePilotCoPilot: Double?,
    val pilotFunctionTimePilotDual: Double?,
    val pilotFunctionTimePilotInstructor: Double?,
    val syntheticTrainingDevicesSessionDate: String?,
    val syntheticTrainingDevicesSessionType: String?,
    val syntheticTrainingDevicesSessionTotalTimeOfSession: Double?,
    val remarksAndEndorsements: String?
){

    fun validate(){
        if(date.toString().isBlank() || date == null) throw EmptyAddFlightFieldException(AddDailyFlightField.Date)
        if(departurePlace.isBlank()) throw EmptyAddFlightFieldException(AddDailyFlightField.DeparturePlace)
        if(departureTime.toString().isBlank() || departureTime == null) throw EmptyAddFlightFieldException(AddDailyFlightField.DepartureTime)
        if(!Pattern.compile("([01]?[0-9]|2[0-3]).([0-5][0-9])").matcher(departureTime.toString()).matches()) throw NotValidInputTimeException(AddDailyFlightField.DepartureTime)
        if(arrivalPlace.isBlank()) throw EmptyAddFlightFieldException(AddDailyFlightField.ArrivalPlace)
        if(arrivalTime.toString().isBlank() || arrivalTime == null) throw EmptyAddFlightFieldException(AddDailyFlightField.ArrivalTime)
        if(aircraftModel.isBlank()) throw EmptyAddFlightFieldException(AddDailyFlightField.Model)
        if(aircraftRegistration.isBlank()) throw EmptyAddFlightFieldException(AddDailyFlightField.Registration)
        if(totalTimeOfFlight.toString().isBlank() || totalTimeOfFlight == null) throw EmptyAddFlightFieldException(AddDailyFlightField.TotalTimeOfFlight)
    }

}