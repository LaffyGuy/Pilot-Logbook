package com.example.pilotlogbook.domain.entities

data class DailyFlight(
    val id: Int? = null,
    val date: Long?,
    val departurePlace: String?,
    val departureTime: Long?,
    val arrivalPlace: String?,
    val arrivalTime: Long?,
    val aircraftModel: String,
    val aircraftRegistration: String,
    val singlePilotTimeSe: Double?,
    val singlePilotTimeMe: Double?,
    val multiPilotTime: Double?,
    val totalTimeOfFlight: Long?,
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
)
