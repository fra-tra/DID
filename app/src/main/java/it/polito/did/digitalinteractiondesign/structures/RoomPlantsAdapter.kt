package it.polito.did.digitalinteractiondesign.structures

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.polito.did.digitalinteractiondesign.R
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
             Glide.with(holder.itemView).load(plants[position].image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imagePlant)

             if (plants[position].isDead) {
                 imagePlant.saturation = 0.15F
             }
             //SET NAVIGATION TO MY PLANT DETAIL
             cardView.setOnClickListener {
                 Log.d("Mostra", "${titlePlantProva.text.toString()}")
                 var bundleActivePlant= bundleOf(Pair("activePlant",plants[position].idIdentification))
                 if(plants[position].isDead) {
                     Navigation.findNavController(cardView).navigate(R.id.action_piante_to_myDeadPlantFragment,bundleActivePlant)
                 }
                 else {
                     Navigation.findNavController(cardView).navigate(R.id.action_piante_to_myPlantFragment,bundleActivePlant)
                 }
             }

             //PASS IMAGE DATA
         }

    }
}