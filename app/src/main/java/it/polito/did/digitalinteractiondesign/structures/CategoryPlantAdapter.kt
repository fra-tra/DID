package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.polito.did.digitalinteractiondesign.ManagerPlantsInfoFirestore
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemCategoryPlantBinding
import java.util.Date.from


class CategoryPlantAdapter (var categories:  MutableList<PlantCategory>)
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

            Glide.with(holder.itemView).load(ManagerPlantsInfoFirestore.hasMapCategoryImage.get(categories[position].name))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(itemCategoryPlantImage)
            //SET TEXT
            var activeCategoryPlantID=categories[position].name
            itemCategoryPlantTitle.text = categories[position].name

            //NAVIGATE TO CATEGORY DETAIL
            itemCategoryPlantImage.setOnClickListener {
                var bundleActivePlant= bundleOf(Pair("activeCategory",activeCategoryPlantID))
                Navigation.findNavController(itemCategoryPlantImage).navigate(R.id.action_discover_to_discoveryCategoryDetailFragment,bundleActivePlant)
            }
        }

    }

    override fun getItemCount(): Int {
        return categories.size
    }




}