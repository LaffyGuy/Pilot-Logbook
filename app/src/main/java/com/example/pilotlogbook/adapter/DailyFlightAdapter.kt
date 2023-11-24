package com.example.pilotlogbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.DailyFlightItemBinding
import com.example.pilotlogbook.domain.entities.DailyFlight

class DailyFlightAdapter: RecyclerView.Adapter<DailyFlightAdapter.DailyFlightViewHolder>() {
    class DailyFlightViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val binding = DailyFlightItemBinding.bind(itemView)
        fun bind(item: DailyFlight){
              with(binding){
                  tvDate.text = item.date
                  tvDeparturePlace.text = item.departurePlace
                  tvDepartureTime.text = item.departureTime.toString()
                  tvArrivalPlace.text = item.arrivalPlace
                  tvArrivalTime.text = item.arrivalTime.toString()
                  tvAircrafyModel.text = item.aircraftModel
                  tvAircraftRegistration.text = item.aircraftRegistration
                  tvSinglePilotTimeSe.text = item.singlePilotTimeSe.toString()
                  tvSinglePilotTimeMe.text = item.singlePilotTimeMe.toString()
                  tvMultiPilotTime.text = item.multiPilotTime.toString()
                  tvTotalTimeOfFlight.text = item.totalTimeOfFlight.toString()
                  tvPickName.text = item.picName
                  tvLandingsDay.text = item.landingsDay.toString()
                  tvLandingsNight.text = item.landingsNight.toString()
                  tvOperationalConditionTimeNight.text = item.operationalConditionTimeNight.toString()
                  tvOperationalConditionTimeIFR.text = item.operationalConditionTimeIfr.toString()
                  tvPilotFunctionTimePilotInComand.text = item.pilotFunctionTimePilotInComand.toString()
                  tvPilotFunctionTimeCoPilot.text = item.pilotFunctionTimePilotCoPilot.toString()
                  tvPilotFunctionTimeDual.text = item.pilotFunctionTimePilotDual.toString()
                  tvPilotFunctionTimeInstructor.text = item.pilotFunctionTimePilotInstructor.toString()
                  tvSyntheticTrainingDevicesSessionDate.text = item.syntheticTrainingDevicesSessionDate.toString()
                  tvSyntheticTrainingDevicesSessionType.text = item.syntheticTrainingDevicesSessionType
                  tvSyntheticTrainingDevicesSessionTotalTimeOfSession.text = item.syntheticTrainingDevicesSessionTotalTimeOfSession.toString()
                  tvRemarksAndEndorsements.text = item.remarksAndEndorsements

              }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyFlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_flight_item, parent, false)
        return DailyFlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyFlightViewHolder, position: Int) {
        val currentDailyFlight = differ.currentList[position]
        holder.bind(currentDailyFlight)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var differCallback = object : DiffUtil.ItemCallback<DailyFlight>(){
        override fun areItemsTheSame(oldItem: DailyFlight, newItem: DailyFlight): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DailyFlight, newItem: DailyFlight): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

}