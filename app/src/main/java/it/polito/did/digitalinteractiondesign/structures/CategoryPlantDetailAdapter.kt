package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemDiscoverPlantsBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemLikedPlantsBinding
import java.util.*
import kotlin.collections.ArrayList

class CategoryPlantDetailAdapter (var plants: List<Plant>)
    : RecyclerView.Adapter<CategoryPlantDetailAdapter.CategoryPlantDetailViewHolder>() , Filterable {
    inner class CategoryPlantDetailViewHolder(val binding: ItemDiscoverPlantsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    var plantFilterList = listOf<Plant>()
    init {
        plantFilterList = plants

    }

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
        holder.binding.apply {
            //SET IMAGE

            //SET TITLE

            itemDiscoverPlantsTitle.text = plants[position].name

            val selectPlantsTextView =
                holder.itemView.findViewById<TextView>(R.id.itemDiscoverPlantsTitle)
            selectPlantsTextView.text = plantFilterList[position].name

            //SET NAVIGATION
            itemDiscoverPlantsLayout.setOnClickListener {
                Navigation.findNavController(itemDiscoverPlantsImage)
                    .navigate(R.id.discoverPlantDetailFragment)
            }

        }
    }

    override fun getItemCount(): Int {
        return plantFilterList.size
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if (charSearch.isEmpty()) {
                    plantFilterList = plants
                } else {
                    val resultList = mutableListOf<Plant>()
                    for (row in plants) {
                        if (row.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    plantFilterList = resultList
                }

                val filterResult = FilterResults()
                filterResult.values = plantFilterList
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                plantFilterList = p1?.values as List<Plant>
                notifyDataSetChanged()
            }

        }

    }
}

