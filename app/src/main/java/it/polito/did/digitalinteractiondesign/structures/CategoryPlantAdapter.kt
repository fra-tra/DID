package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemCategoryPlantBinding


class CategoryPlantAdapter (var categories: List<String>)
    : RecyclerView.Adapter<CategoryPlantAdapter.CategoryPlantViewHolder>(){
    inner class CategoryPlantViewHolder(val binding: ItemCategoryPlantBinding) :RecyclerView.ViewHolder(binding.root)
    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryPlantViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryPlantBinding.inflate(layoutInflater, parent, false)
        return CategoryPlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryPlantViewHolder, position: Int) {
        holder.binding.apply {
            //SET IMAGE

            //SET TEXT
            itemCategoryPlantTitle.text = categories[position]

            //NAVIGATE TO CATEGORY DETAIL
            itemCategoryPlantImage.setOnClickListener {
                Navigation.findNavController(itemCategoryPlantImage).navigate(R.id.action_discover_to_discoveryCategoryDetailFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}