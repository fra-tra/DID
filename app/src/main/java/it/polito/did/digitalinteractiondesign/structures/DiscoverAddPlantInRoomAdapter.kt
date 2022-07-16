package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemCategoryPlantBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomImageBinding

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

            //HIDE OVERLAY IF SELECTED
            selectionOverlay.isInvisible = true

            roomCardImage.setOnClickListener{
               Navigation.findNavController(roomCardImage).navigate(R.id.action_addPlantNameAndRoomFragment_to_addPlantVaseFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}