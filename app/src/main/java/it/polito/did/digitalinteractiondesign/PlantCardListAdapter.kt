package it.polito.did.digitalinteractiondesign

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantCardBinding
import java.security.AccessController.getContext


class PlantCardListAdapter (var plants: List<Plant>)
    : RecyclerView.Adapter<PlantCardListAdapter.PlantCardListViewHolder>(){

    inner class PlantCardListViewHolder(val binding: ItemPlantCardBinding) :RecyclerView.ViewHolder(binding.root)
    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantCardListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlantCardBinding.inflate(layoutInflater, parent, false)
        return PlantCardListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantCardListViewHolder, position: Int) {

       holder.binding.apply{
           plantCardName.text = plants[position].name
           if(plants[position].isDead){
               plantCard.setCardBackgroundColor(context.getResources().getColor(R.color.light_grey))
               plantDetailBtn.setOnClickListener {
                   //SHOW DETAIL DEAD PLANT
               }
           }
           else {
               plantCard.setCardBackgroundColor(context.getResources().getColor(R.color.light_green))
               plantDetailBtn.setOnClickListener {
                   //SHOW DETAIL MY PLANT
               }

           }

       }
    }

    override fun getItemCount(): Int {
        return plants.size
    }


}