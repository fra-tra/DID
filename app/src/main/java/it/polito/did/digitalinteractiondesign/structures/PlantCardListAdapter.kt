package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantCardBinding

//ADAPTER PER LISTA DI PIANTE IN LE MIE PIANTE - STANZA / GRAVEYARD
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
           //SET PLANT TITLE
           plantCardName.text = plants[position].name

           //SET PLANT IMAGE
           Glide.with(holder.itemView).load(plants[position].image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imagePlant)

           //SET CARD BACKGROUND COLOR AND NAVIGATION IF PLANT IS DEAD OR OTHERWISE
           if(plants[position].isDead){

               plantCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_purple))
               imagePlant.saturation = 0.15F
               plantDetailBtn.setOnClickListener {
                 //invio l'id per l'identificazione
                   var bundleActivePlant= bundleOf(Pair("activePlant",plants[position].idIdentification))
                   //SHOW DETAIL DEAD PLANT
                   Navigation.findNavController(plantDetailBtn).navigate(R.id.action_graveyardFragment_to_myDeadPlantFragment,bundleActivePlant)
               }
           }
           else {
               plantCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_green))
               plantDetailBtn.setOnClickListener {
                   //SHOW DETAIL MY PLANT
                  // Log.d("PositionPLant", plants[position].name)
                   var bundleActivePlant= bundleOf(Pair("activePlant",plants[position].idIdentification))
                   Navigation.findNavController(plantDetailBtn).navigate(R.id.action_roomFragment_to_myPlantFragment, bundleActivePlant)
               }

           }

       }
    }

    override fun getItemCount(): Int {
        return plants.size
    }


}