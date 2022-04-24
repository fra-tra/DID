package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantImageBinding

class ProfileLikedPlantAdapter (val plants : List<Plant>) : RecyclerView.Adapter<ProfileLikedPlantAdapter.ProfileLikedPlantViewHolder>(){
    private lateinit var context : Context

    inner class ProfileLikedPlantViewHolder(val binding: ItemPlantImageBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileLikedPlantViewHolder {
        context=parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding=ItemPlantImageBinding.inflate(layoutInflater, parent,false)
        return ProfileLikedPlantViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ProfileLikedPlantViewHolder, position: Int) {
       holder.binding.apply {titlePlantProva.text=plants[position].name}
    }

    override fun getItemCount(): Int {
        return plants.size
    }
}