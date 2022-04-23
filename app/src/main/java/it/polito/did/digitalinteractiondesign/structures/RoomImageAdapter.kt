package it.polito.did.digitalinteractiondesign.structures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantImageBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomImageBinding

class RoomImageAdapter (var rooms: List<Room>) : RecyclerView.Adapter<RoomImageAdapter.RoomImageViewHolder> (){
    var selectedItemPos = -1
    var lastItemSelectedPos = -1
    inner class RoomImageViewHolder (val binding: ItemRoomImageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardView.setOnClickListener {
                selectedItemPos = bindingAdapterPosition
                if(lastItemSelectedPos == -1)
                    lastItemSelectedPos = selectedItemPos
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                }
                notifyItemChanged(selectedItemPos)
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRoomImageBinding.inflate(layoutInflater, parent, false)
        return RoomImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomImageViewHolder, position: Int) {

        holder.binding.apply{
            roomCardName.text = rooms[position].name

            //SET IMAGE

            //SHOW OVERLAY IF SELECTED
            //selectionOverlay.isVisible = false
           // selectionOverlay.isVisible = position == selectedItemPos
            selectionOverlay.isInvisible = position != selectedItemPos

        }
    }

    override fun getItemCount(): Int {
        return rooms.size
    }
}