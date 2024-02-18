package com.example.pilotlogbook.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyFlight(
    val id: Int? = null,
    val date: Long?,
    val departurePlace: String?,
    val departureTime: Long?,
    val arrivalPlace: String?,
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
): Parcelable
