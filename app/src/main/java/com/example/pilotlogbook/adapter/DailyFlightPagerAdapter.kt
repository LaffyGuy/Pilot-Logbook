package com.example.pilotlogbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pilotlogbook.R
import com.example.pilotlogbook.databinding.DailyFlightItemBinding
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.utils.convertLongToDate
import com.example.pilotlogbook.utils.convertLongToTime
import java.text.SimpleDateFormat
import java.util.Locale


interface DailyFlightAction {

    fun dailyFlightDetails(dailyFlight: DailyFlight)

}
class DailyFlightPagerAdapter(private val dailyFlightAction: DailyFlightAction): PagingDataAdapter<DailyFlight, DailyFlightPagerAdapter.Holder>(DailyFlightDiffCallBack()), View.OnClickListener {
    class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
           val binding = DailyFlightItemBinding.bind(itemView)
           fun bind(item: DailyFlight){
               with(binding){
                   tvDate.text = convertLongToDate(item.date ?: 0)
                   tvDeparturePlace.text = item.departurePlace
                   tvDepartureTime.text = convertLongToTime(item.departureTime ?: 0)
                   tvArrivalPlace.text = item.arrivalPlace
                   tvArrivalTime.text = convertLongToTime(item.arrivalTime ?: 0)
                   tvModel.text = item.aircraftModel
                   tvRegistration.text = item.aircraftRegistration
                   tvTotalTimeToFlight.text = convertLongToTime(item.totalTimeOfFlight ?: 0)
               }
           }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_flight_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentDailyFlight = getItem(position) ?: return
        holder.bind(currentDailyFlight)

        holder.itemView.tag = currentDailyFlight

        holder.itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        val dailyFlight = p0.tag as DailyFlight
        dailyFlightAction.dailyFlightDetails(dailyFlight)
    }
}

class DailyFlightDiffCallBack: DiffUtil.ItemCallback<DailyFlight>() {
    override fun areItemsTheSame(oldItem: DailyFlight, newItem: DailyFlight): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DailyFlight, newItem: DailyFlight): Boolean {
        return oldItem == newItem
    }
}