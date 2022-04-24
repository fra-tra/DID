package it.polito.did.digitalinteractiondesign.structures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomImageBinding

class AddRoomAdapter (var rooms: List<Room>): RecyclerView.Adapter<AddRoomAdapter.AddRoomViewHolder>(){

    inner class AddRoomViewHolder (val binding: ItemRoomImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddRoomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomImageBinding.inflate(layoutInflater, parent, false)
        return AddRoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddRoomViewHolder, position: Int) {
        holder.binding.apply{
            roomCardName.text = rooms[position].name
            selectionOverlay.isInvisible = true

            //SET IMAGE

            cardView.setOnClickListener {
                //ADD SELECTED ROOM TO GLOBAL ROOMS ARRAY FIRST
                Navigation.findNavController(cardView).navigateUp()
            }
        }

    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}