package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.FragmentDetailsDailyFlightBinding
import com.example.pilotlogbook.utils.convertLongToDate
import com.example.pilotlogbook.utils.convertLongToTime


class DetailsDailyFlightFragment : Fragment() {
    lateinit var bindingClass: FragmentDetailsDailyFlightBinding
    private val args: DetailsDailyFlightFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentDetailsDailyFlightBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInfoFromDailyFlightFragments()
        
    }

    private fun getInfoFromDailyFlightFragments(){
        with(bindingClass){
            tvDateValue.text = convertLongToDate(args.dailyFlight.date!!)
            tvDeparturePlaceValue.text = args.dailyFlight.departurePlace
            tvDepartureTimeValue.text = convertLongToTime(args.dailyFlight.departureTime!!)
            tvArrivalPlaceValue.text = args.dailyFlight.arrivalPlace
            tvArrivalTimeValue.text = convertLongToTime(args.dailyFlight.arrivalTime!!)
            tvAircraftModelValue.text = args.dailyFlight.aircraftModel
            tvAircraftRegistrationValue.text = args.dailyFlight.aircraftRegistration
            tvSinglePilotTimeSEValue.text = args.dailyFlight.singlePilotTimeSe.toString()
            tvSinglePilotTimeMEValue.text = args.dailyFlight.singlePilotTimeMe.toString()
            tvMultiPilotTimeValue.text = args.dailyFlight.multiPilotTime.toString()
            tvTotalTimeOfFlightValue.text = args.dailyFlight.totalTimeOfFlight.toString()
            tvPicNameValue.text = args.dailyFlight.picName
            tvDayValue.text = args.dailyFlight.landingsDay.toString()
            tvNightValue.text = args.dailyFlight.landingsNight.toString()
            tvOPTNightValue.text = args.dailyFlight.operationalConditionTimeNight.toString()
            tvOPTIfrValue.text = args.dailyFlight.operationalConditionTimeIfr.toString()
            tvPilotInComandValue.text = args.dailyFlight.pilotFunctionTimePilotInComand.toString()
            tvCoPilotValue.text = args.dailyFlight.pilotFunctionTimePilotCoPilot.toString()
            tvDualValue.text = args.dailyFlight.pilotFunctionTimePilotDual.toString()
            tvInstructorValue.text = args.dailyFlight.pilotFunctionTimePilotInstructor.toString()
            tvSTDSDateValue.text = args.dailyFlight.syntheticTrainingDevicesSessionDate
            tvSTDSTypeValue.text = args.dailyFlight.syntheticTrainingDevicesSessionType
            tvSTDSTotalTimeOfSessionValue.text = args.dailyFlight.syntheticTrainingDevicesSessionTotalTimeOfSession.toString()
            tvRemarksAndEndorsementsValue.text = args.dailyFlight.remarksAndEndorsements
        }
    }


}