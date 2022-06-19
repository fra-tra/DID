package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemLikedPlantsBinding


class LikedAndPopularPlantsAdapter (var plants: List<Plant>)
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
            //SET IMAGE

            //SET TITLE
            itemLikedPlantsTitle.text = plants[position].name

            itemLikedPlantsImage.setOnClickListener {
                //NAVIGATE TO SELECTED PLANT
                Navigation.findNavController(itemLikedPlantsImage).navigate(R.id.action_discover_to_discoverPlantDetailFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return plants.size
    }
}