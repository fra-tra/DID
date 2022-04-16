package it.polito.did.digitalinteractiondesign.structures

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantImageBinding

class RoomPlantsAdapter (var plants: List <Plant>)
    : RecyclerView.Adapter<RoomPlantsAdapter.RoomPlantViewHolder>(){

    inner class RoomPlantViewHolder(val binding: ItemPlantImageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomPlantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlantImageBinding.inflate(layoutInflater, parent, false)
        return RoomPlantViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    override fun onBindViewHolder(holder: RoomPlantViewHolder, position: Int) {
         holder.binding.apply {
             titlePlantProva.text = plants[position].name

             //SET NAVIGATION TO MY PLANT DETAIL
             cardView.setOnClickListener {
                 Log.d("Mostra", "${titlePlantProva.text.toString()}")
             }

             //PASS IMAGE DATA
         }

    }
}