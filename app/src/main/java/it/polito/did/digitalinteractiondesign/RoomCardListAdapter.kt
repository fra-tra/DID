package it.polito.did.digitalinteractiondesign

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomCardBinding

class RoomCardListAdapter(var rooms: List<Room>): RecyclerView.Adapter<RoomCardListAdapter.RoomCardListViewHolder>() {

    inner class RoomCardListViewHolder(val binding: ItemRoomCardBinding): RecyclerView.ViewHolder(binding.root)
    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomCardListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomCardBinding.inflate(layoutInflater, parent, false)
        return RoomCardListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomCardListViewHolder, position: Int) {
        holder.binding.apply{
            roomCardTitle.text = rooms[position].name


            val rvRoomPlantsSummaryAdapter = RoomPlantsAdapter(rooms[position].plants)
            rvRoomPlantSummary.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvRoomPlantSummary.adapter = rvRoomPlantsSummaryAdapter


            //VERIFY IF ROOM IS PLANT GRAVEYARD: POSITION OR TITLE?
            if(rooms[position].name == "Plant Graveyard") {
                roomCard.setCardBackgroundColor(context.getResources().getColor(R.color.light_grey))
            }

            //SHOW DETAIL ROOM PLANTS
            seeAllRoomPlantsBtn.setOnClickListener {
                Log.d("Mostra", "tutte le piante in ${roomCardTitle.text.toString()}")
            }
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}