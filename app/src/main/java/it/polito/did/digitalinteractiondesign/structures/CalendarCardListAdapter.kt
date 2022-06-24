package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemCalendarRoomCardBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomCardBinding

class CalendarCardListAdapter(var rooms: List<Room>): RecyclerView.Adapter<CalendarCardListAdapter.CalendarCardListViewHolder>() {

    inner class CalendarCardListViewHolder(val binding: ItemCalendarRoomCardBinding): RecyclerView.ViewHolder(binding.root)
    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarCardListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCalendarRoomCardBinding.inflate(layoutInflater, parent, false)
        return CalendarCardListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarCardListViewHolder, position: Int) {
        holder.binding.apply{
            val rvCalendarPlantsAdapter = CalendarPlantsAdapter(rooms[position].plants)
            RecyclerCalendar.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            RecyclerCalendar.adapter = rvCalendarPlantsAdapter
            RoomCalendarTitle.text = rooms[position].name
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}