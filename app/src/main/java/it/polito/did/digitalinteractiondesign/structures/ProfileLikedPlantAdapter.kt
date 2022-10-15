package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.databinding.ItemLikedPlantsProfiloBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantImageBinding

class ProfileLikedPlantAdapter (val plants : List<PlantsInfo>) : RecyclerView.Adapter<ProfileLikedPlantAdapter.ProfileLikedPlantViewHolder>(){
    private lateinit var context : Context

    inner class ProfileLikedPlantViewHolder(val binding: ItemLikedPlantsProfiloBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileLikedPlantViewHolder {
        context=parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding=ItemLikedPlantsProfiloBinding.inflate(layoutInflater, parent,false)
        return ProfileLikedPlantViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ProfileLikedPlantViewHolder, position: Int) {
       holder.binding.apply {
           Glide.with(holder.itemView).load(plants[position].imageUrl).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imagePlant)
           var activeCategoryPlantID=plants[position].category+"_"+plants[position].name
           titlePlantProva.text=plants[position].name
           cardView.setOnClickListener {
               val bottomNav: BottomNavigationView = (context as Home_Activity).findViewById(R.id.bottomNavigationView)
               var bundleActivePlant= bundleOf(Pair("activePlantInfo",activeCategoryPlantID))
               bottomNav.selectedItemId = R.id.discover
               Navigation.findNavController(cardView).navigate(R.id.discoverPlantDetailFragment,bundleActivePlant)
           }
       }
    }

    override fun getItemCount(): Int {
        return plants.size
    }
}