package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemDiscoverPlantsBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemLikedPlantsBinding

class CategoryPlantDetailAdapter (var plants: List<Plant>)
    : RecyclerView.Adapter<CategoryPlantDetailAdapter.CategoryPlantDetailViewHolder>()  {
    inner class CategoryPlantDetailViewHolder(val binding: ItemDiscoverPlantsBinding) :RecyclerView.ViewHolder(binding.root)
    private lateinit var context : Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryPlantDetailViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDiscoverPlantsBinding.inflate(layoutInflater, parent, false)
        return CategoryPlantDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryPlantDetailViewHolder, position: Int) {
        holder.binding.apply{
            //SET IMAGE

            //SET TITLE
            itemDiscoverPlantsTitle.text = plants[position].name

            //SET NAVIGATION
            itemDiscoverPlantsLayout.setOnClickListener {
                Navigation.findNavController(itemDiscoverPlantsImage).navigate(R.id.action_discoveryCategoryDetailFragment_to_discoverPlantDetailFragment)
            }
        }


    }

    override fun getItemCount(): Int {
        return plants.size
    }
}