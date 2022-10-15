package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.polito.did.digitalinteractiondesign.ManagerPlantsInfoFirestore
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemCategoryPlantBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomImageBinding
import it.polito.did.digitalinteractiondesign.fragments.AddPlantNameAndRoomFragment
import it.polito.did.digitalinteractiondesign.fragments.AddPlantVaseFragment

class DiscoverAddPlantInRoomAdapter (var rooms: List<Room>) : RecyclerView.Adapter<DiscoverAddPlantInRoomAdapter.DiscoverAddPlantInRoomViewHolder> () {



    inner class DiscoverAddPlantInRoomViewHolder(val binding: ItemRoomImageBinding) : RecyclerView.ViewHolder(binding.root) {}
    private lateinit var context : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverAddPlantInRoomViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomImageBinding.inflate(layoutInflater, parent, false)
        return DiscoverAddPlantInRoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscoverAddPlantInRoomViewHolder, position: Int) {
        holder.binding.apply{
            //SET TITLE
            roomCardName.text = rooms[position].name

            //SET IMAGE
            Glide.with(holder.itemView).load(ManagerPlantsInfoFirestore.hasMapRoomsImage.get(rooms[position].name))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(roomCardImage)

            //HIDE OVERLAY IF SELECTED
            selectionOverlay.isInvisible = true

            roomCardImage.setOnClickListener{

                // Da migliorare con l'ìd dell'immagine che arriverà
                Navigation.findNavController(roomCardImage).navigate(R.id.action_addPlantNameAndRoomFragment_to_addPlantVaseFragment)
                Log.d("Stanza id", "${roomCardName.id } + ${roomCardName.text} + ${roomCardImage.id}" )
                AddPlantVaseFragment.roomPlant= roomCardName.text.toString()
                //Devo avere la lista delle Image ed in base al id image la vado a passare
                //AddPlantVaseFragment.plantToAdd.image= IMAGE[roomCardImage.id]
            }


        }



    }

    override fun getItemCount(): Int {
        return rooms.size
    }


}