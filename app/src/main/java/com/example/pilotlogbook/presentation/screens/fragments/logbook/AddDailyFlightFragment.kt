package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pilotlogbook.R
import com.example.pilotlogbook.data.validation.DailyFlightForm
import com.example.pilotlogbook.data.validation.ValidationResult
import com.example.pilotlogbook.databinding.FragmentAddDailyFlightBinding
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.DailyFlightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDailyFlightFragment : Fragment() {
   lateinit var bindingClass: FragmentAddDailyFlightBinding
   private val dailyFlightViewModel by viewModels<DailyFlightViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentAddDailyFlightBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValidation()

        bindingClass.btnAddDailyFlightLog.setOnClickListener {
            dailyFlightViewModel.state.observe(viewLifecycleOwner){ state ->
                Log.d("MyTag4", "State - $state")
                if(state){
                    findNavController().popBackStack()
                }
            }
            addDailyFlight()
        }

    }

    private fun addDailyFlight(){
        val dailyFlightForm = DailyFlightForm(
            bindingClass.etDate.text.toString(),
            bindingClass.etDeparturePlace.text.toString(),
            bindingClass.etDepartureTime.text.toString().toDouble(),
            bindingClass.etArrivalPlace.text.toString(),
            bindingClass.etArrivalTime.text.toString().toDouble(),
            bindingClass.etAircraftModel.text.toString(),
            bindingClass.etAircraftRegistration.text.toString(),
            bindingClass.etSinglePilotSe.text.toString().toDouble(),
            bindingClass.etSinglePilotMe.text.toString().toDouble(),
            bindingClass.etMultiPilotTime.text.toString().toDouble(),
            bindingClass.etTotalTimeOfFlight.text.toString().toDouble(),
            bindingClass.etPicName.text.toString(),
            bindingClass.etLandingsDay.text.toString().toInt(),
            bindingClass.etLandingsNight.text.toString().toInt(),
            bindingClass.etOperationalNight.text.toString().toDouble(),
            bindingClass.etOperationalIfr.text.toString().toDouble(),
            bindingClass.etPilotFunctionTimePilotInComand.text.toString().toDouble(),
            bindingClass.etPilotFunctionTimeCoPilot.text.toString().toDouble(),
            bindingClass.etPilotFunctionTimeDual.text.toString().toDouble(),
            bindingClass.etPilotFunctionTimeInstractor.text.toString().toDouble(),
            bindingClass.etSyntheticTrainingDevicesSessionDate.text.toString(),
            bindingClass.etSyntheticTrainingDevicesSessionType.text.toString(),
            bindingClass.etSyntheticTrainingDevicesSessionTotalTimeOfSession.text.toString().toDouble(),
            bindingClass.etRemarksAndEndorsements.text.toString())
        dailyFlightViewModel.addDailyFlight(dailyFlightForm)
    }

    private fun observeValidation(){
        dailyFlightViewModel.validation.observe(viewLifecycleOwner){
            if(it.date is ValidationResult.Failed){
                bindingClass.etDate.apply {
                    requestFocus()
                    error = it.date.error
                }
            }
            if(it.departurePlace is ValidationResult.Failed){
                bindingClass.etDeparturePlace.apply {
                    requestFocus()
                    error = it.departurePlace.error
                }
            }
            if(it.departureTime is ValidationResult.Failed){
                bindingClass.etDepartureTime.apply {
                    requestFocus()
                    error = it.departureTime.error
                }
            }
            if(it.arrivalPlace is ValidationResult.Failed){
                bindingClass.etArrivalPlace.apply {
                    requestFocus()
                    error = it.arrivalPlace.error
                }
            }
            if(it.arrivalTime is ValidationResult.Failed){
                bindingClass.etArrivalTime.apply {
                    requestFocus()
                    error = it.arrivalTime.error
                }
            }
            if(it.model is ValidationResult.Failed){
                bindingClass.etAircraftModel.apply {
                    requestFocus()
                    error = it.model.error
                }
            }
            if(it.registration is ValidationResult.Failed){
                bindingClass.etAircraftRegistration.apply {
                    requestFocus()
                    error = it.registration.error
                }
            }
        }
    }


}