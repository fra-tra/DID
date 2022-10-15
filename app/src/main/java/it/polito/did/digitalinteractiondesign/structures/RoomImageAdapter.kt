package it.polito.did.digitalinteractiondesign.structures

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlantsInfoFirestore
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantImageBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomImageBinding
import it.polito.did.digitalinteractiondesign.fragments.MyPlantSettingsFragment
import it.polito.did.digitalinteractiondesign.fragments.MyPlantSettingsRoomFragment

// adapter per recycler view che mostra la selezione di stanze
class RoomImageAdapter (var rooms: List<Room>) : RecyclerView.Adapter<RoomImageAdapter.RoomImageViewHolder> (){

    // gestione single item selection
    //da aggiornare per inizializzare come selezione la stanza dove si trova la pianta
    //selectedItemPos e lastItemSelectedPos devono essere inizializzati con lo stesso valore
    var selectedItemPos = 0
    var lastItemSelectedPos = 0
    var activePlantID=""


    inner class RoomImageViewHolder (val binding: ItemRoomImageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardView.setOnClickListener {
                selectedItemPos = bindingAdapterPosition
                Log.d("selectedItemPos", selectedItemPos.toString())
                if(lastItemSelectedPos == -1) {

                    lastItemSelectedPos = selectedItemPos
                }
                else {
                    notifyItemChanged(lastItemSelectedPos)
                    lastItemSelectedPos = selectedItemPos
                    ManagerFirebase.updateValuePlantAlive(
                        activePlantID,
                        "Room",
                        rooms[position].name
                    )
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
            //SET TITLE
            roomCardName.text = rooms[position].name


            //SET IMAGE
            Glide.with(holder.itemView).load(ManagerPlantsInfoFirestore.hasMapRoomsImage.get(rooms[position].name))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(roomCardImage)

            //SHOW OVERLAY IF SELECTED
            selectionOverlay.isInvisible = position != selectedItemPos




            //ManagerFirebase.updateValuePlantAlive(activePlantID.toString(),"Room",rooms[position].name)
            Log.d("PositionLAST", activePlantID +" In " +rooms[position].name)

        }
    }






    override fun getItemCount(): Int {
        return rooms.size
    }
}