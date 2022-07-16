package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemCalendarSwitchCardBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantImageBinding

class CalendarPlantsAdapter (var plants: List <Plant>)
    : RecyclerView.Adapter<CalendarPlantsAdapter.CalendarPlantsViewHolder>(){

    inner class CalendarPlantsViewHolder(val binding: ItemCalendarSwitchCardBinding): RecyclerView.ViewHolder(binding.root)
    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarPlantsViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCalendarSwitchCardBinding.inflate(layoutInflater, parent, false)
        return CalendarPlantsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    override fun onBindViewHolder(holder: CalendarPlantsViewHolder, position: Int) {
        holder.binding.apply {
             val calendarChild = plants[position]

             nestedRVText00.text = calendarChild.name
             nestedRVSwitch00.isChecked = calendarChild.switchStatus


             if (calendarChild.switchStatus) {
                 //  holder.infoCalendarButton.setVisibility(View.VISIBLE);
                 switchCard.setCardBackgroundColor(Color.WHITE)
                 infoSelectedRange.setVisibility(View.VISIBLE)
                 selectDateRange.setVisibility(View.VISIBLE)
             } else {
                 // holder.infoCalendarButton.setVisibility(View.INVISIBLE);
                 switchCard.setCardBackgroundColor(
                     ContextCompat.getColor(
                         context,
                         R.color.light_grey
                     )
                 )
                 infoSelectedRange.setVisibility(View.GONE)
                 selectDateRange.setVisibility(View.GONE)
             }

             val materialDateBuilder: MaterialDatePicker.Builder<*> =
                 MaterialDatePicker.Builder.dateRangePicker()

             materialDateBuilder.setTitleText("SELECT A DATE")

             //disable past days

             //disable past days
             val now = MaterialDatePicker.todayInUtcMilliseconds()
             val constraintsBuilder =
                 CalendarConstraints.Builder().setValidator(DateValidatorPointForward.from(now))
             materialDateBuilder.setCalendarConstraints(constraintsBuilder.build())


             val datePicker = materialDateBuilder.build()

             nestedRVSwitch00.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                 if (isChecked) {
                     // infoCalendarButton.setVisibility(View.VISIBLE);
                     switchCard.setCardBackgroundColor(Color.WHITE)
                     selectDateRange.setVisibility(View.VISIBLE)
                     infoSelectedRange.setVisibility(View.VISIBLE)
                     // apri custom calendar
                 } else {
                     // infoCalendarButton.setVisibility(View.INVISIBLE);
                     switchCard.setCardBackgroundColor(
                         ContextCompat.getColor(
                             context,
                             R.color.light_grey
                         )
                     )
                     infoSelectedRange.setVisibility(View.GONE)
                     selectDateRange.setVisibility(View.GONE)
                 }
                 calendarChild.switchStatus = isChecked
                 Log.d("switchStatus: ", calendarChild.switchStatus.toString())
             })

             infoSelectedRange.setOnClickListener(View.OnClickListener {
             findNavController(infoSelectedRange)
                 .navigate(R.id.action_calendarizzazione_to_selectCalendarRangeFragment)
         })
             //PASS IMAGE DATA
         }

    }
}