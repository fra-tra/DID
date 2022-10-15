package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemLikedPlantsBinding


class LikedAndPopularPlantsAdapter (var plants: List<PlantsInfo>)
    : RecyclerView.Adapter<LikedAndPopularPlantsAdapter.LikedAndPopularPlantsViewHolder>() {
    inner class LikedAndPopularPlantsViewHolder(val binding: ItemLikedPlantsBinding) :RecyclerView.ViewHolder(binding.root)
    private lateinit var context : Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LikedAndPopularPlantsViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLikedPlantsBinding.inflate(layoutInflater, parent, false)
        return LikedAndPopularPlantsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikedAndPopularPlantsViewHolder, position: Int) {
        holder.binding.apply {
            var activeCategoryPlantID=plants[position].category+"_"+plants[position].name
            //SET IMAGE
            Glide.with(holder.itemView).load(plants[position].imageUrl).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(itemLikedPlantsImage)
            //SET TITLE
            itemLikedPlantsTitle.text = plants[position].name

            itemLikedPlantsImage.setOnClickListener {
                var bundleActivePlant= bundleOf(Pair("activePlantInfo",activeCategoryPlantID))
                //NAVIGATE TO SELECTED PLANT
                Navigation.findNavController(itemLikedPlantsImage).navigate(R.id.action_discover_to_discoverPlantDetailFragment, bundleActivePlant)
            }
        }
    }

    override fun getItemCount(): Int {
        return plants.size
    }
}