package com.example.pilotlogbook.presentation.screens.fragments.logbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pilotlogbook.data.validation.DailyFlightForm
import com.example.pilotlogbook.databinding.FragmentAddDailyFlightBinding
import com.example.pilotlogbook.presentation.viewmodels.logbookviewmodel.AddDailyFlightViewModel
import com.example.pilotlogbook.utils.Constance.NO_ERROR_MESSAGE
import com.example.pilotlogbook.utils.SelectDate
import com.example.pilotlogbook.utils.SelectTime
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class AddDailyFlightFragment : Fragment(){
   lateinit var bindingClass: FragmentAddDailyFlightBinding
   private val addDailyFlightViewModel by viewModels<AddDailyFlightViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        bindingClass = FragmentAddDailyFlightBinding.inflate(layoutInflater)
        return bindingClass.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observe()

        observeNavigate()

        addDailyFlightViewModel.date.observe(viewLifecycleOwner){ date ->
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val data = formatter.format(date)
            bindingClass.tvDate.text = data
        }

        addDailyFlightViewModel.departureTime.observe(viewLifecycleOwner){ departureTime ->
            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            val time = formatter.format(departureTime)
            bindingClass.tvDepartureTime.text = time
        }

        addDailyFlightViewModel.arrivalTime.observe(viewLifecycleOwner){ arrivalTime ->
            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            val arrivalTime = formatter.format(arrivalTime)
            bindingClass.tvArrivalTime.text = arrivalTime
        }

        val datePickerDialog = SelectDate(requireContext())

        bindingClass.tvDate.setOnClickListener {
            datePickerDialog.showDatePicker { calendar ->
                updateLable(calendar)

            }
        }

        val timePickerDialog = SelectTime(requireContext())

        bindingClass.tvDepartureTime.setOnClickListener {
            timePickerDialog.showTimePickerDialog { calendar ->
                updateDepartureTimeLable(calendar)
            }
        }

        val timeArrivalPickerDialog = SelectTime(requireContext())

        bindingClass.tvArrivalTime.setOnClickListener {
            timeArrivalPickerDialog.showTimePickerDialog { calendar ->
                updateArrivalTimeLable(calendar)
            }
        }

        bindingClass.btnAddDailyFlight.setOnClickListener {
            addDailyFlight()
        }

    }


    private fun updateLable(calendar: Calendar) {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = formatter.format(calendar.timeInMillis)
        bindingClass.tvDate.text = date
        addDailyFlightViewModel.setDate(calendar.timeInMillis)
    }

    private fun updateDepartureTimeLable(calendar: Calendar){
        bindingClass.tvDepartureTime.text = formatter(calendar)
        addDailyFlightViewModel.setDepartureTime(calendar.timeInMillis)
    }

    private fun updateArrivalTimeLable(calendar: Calendar){
        bindingClass.tvArrivalTime.text = formatter(calendar)
        addDailyFlightViewModel.setArrivalTime(calendar.timeInMillis)
    }

    private fun formatter(calendar: Calendar): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())

        return formatter.format(calendar.time)
    }

    private fun observeNavigate(){
        addDailyFlightViewModel.navigate.observe(viewLifecycleOwner){ state ->
            if(state){
                findNavController().popBackStack()
            }
        }
    }

    private fun addDailyFlight(){
        val date = addDailyFlightViewModel.date.value
        val departureTime = addDailyFlightViewModel.departureTime.value
        val arrivalTime = addDailyFlightViewModel.arrivalTime.value
        val dailyFlightForm = DailyFlightForm(
            date,
            bindingClass.etDeparturePlace.text.toString(),
            departureTime,
            bindingClass.etArrivalPlace.text.toString(),
            arrivalTime,
            bindingClass.etAircraftModel.text.toString(),
            bindingClass.etAircraftRegistration.text.toString(),
            bindingClass.etSinglePilotTimeSe.text.toString().toLongOrNull(),
            bindingClass.etSinglePilotTimeMe.text.toString().toLongOrNull(),
            bindingClass.etMultiPilotTime.text.toString().toLongOrNull(),
            convertTime(),
            bindingClass.etPicName.text.toString(),
            bindingClass.etLandingsDay.text.toString().toIntOrNull(),
            bindingClass.etLandingsNight.text.toString().toIntOrNull(),
            bindingClass.etOPCNight.text.toString().toLongOrNull(),
            bindingClass.etOPCIfr.text.toString().toLongOrNull(),
            bindingClass.etPilotInComand.text.toString().toLongOrNull(),
            bindingClass.etCoPilot.text.toString().toLongOrNull(),
            bindingClass.etDual.text.toString().toLongOrNull(),
            bindingClass.etInstructor.text.toString().toLongOrNull(),
            bindingClass.etSTDSDate.text.toString(),
            bindingClass.etSTDSType.text.toString(),
            bindingClass.etSTDSTotalTimeOfSession.text.toString().toLongOrNull(),
            bindingClass.etRemarksAndEndorsements.text.toString())
        addDailyFlightViewModel.addDailyFlight(dailyFlightForm)
    }

    private fun observe(){
        addDailyFlightViewModel.state.observe(viewLifecycleOwner){
            fillErrorTextView(bindingClass.tvDate, it.dateErrorMessage)
            fillError(bindingClass.etDeparturePlace, it.departurePlaceErrorMessage)
            fillErrorTextView(bindingClass.tvDepartureTime, it.departureTimeErrorMessage)
            fillError(bindingClass.etArrivalPlace, it.arrivalPlaceErrorMessage)
            fillErrorTextView(bindingClass.tvArrivalTime, it.arrivalTimeErrorMessage)
            fillError(bindingClass.etAircraftModel, it.modelErrorMessage)
            fillError(bindingClass.etAircraftRegistration, it.registrationErrorMessage)
            fillError(bindingClass.etTotalTimeOfFlight, it.totalTimeOfFlightErrorMessage)
        }
    }

    private fun fillError(input: EditText, stringRes: Int){
        if(stringRes == NO_ERROR_MESSAGE){
            input.error = null
        }else{
            input.requestFocus()
            input.error = getString(stringRes)
        }
    }

    private fun fillErrorTextView(input: TextView, stringRes: Int){
        if(stringRes == NO_ERROR_MESSAGE){
            input.error = null
        }else{
            input.requestFocus()
            input.error = getString(stringRes)
        }
    }

    private fun convertTime(): Long {
        val input = bindingClass.etTotalTimeOfFlight.text.toString()
        val parts = input.split(".")
        val hours = parts[0].toIntOrNull() ?: 0
        val minutes = if(parts.size > 1) parts[1].toIntOrNull() ?: 0 else 0

        return (hours * 60 * 60 * 1000 + minutes * 60 * 1000).toLong()
    }

}