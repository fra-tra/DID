package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ItemDiscoverPlantsBinding
import java.net.URL
import java.util.*


class CategoryPlantDetailAdapter (var plants: List<PlantsInfo>):RecyclerView.Adapter<CategoryPlantDetailAdapter.CategoryPlantDetailViewHolder>() , Filterable {
    inner class CategoryPlantDetailViewHolder(val binding: ItemDiscoverPlantsBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    var plantFilterList = listOf<PlantsInfo>()
    init {
        plantFilterList = plants

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryPlantDetailViewHolder {
        context = parent.context
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        // code block
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDiscoverPlantsBinding.inflate(layoutInflater, parent, false)
        return CategoryPlantDetailViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CategoryPlantDetailViewHolder, position: Int) {
        holder.binding.apply {
            //SET IMAGE
            //val url = URL(plants[position].imageUrl)
            //val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
           //itemDiscoverPlantsImage.setImageBitmap(bmp)
            var activePlantInfo=plants[position].category +"_"+plants[position].name
            Glide.with(context).load(plants[position].imageUrl).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(itemDiscoverPlantsImage)

            //SET TITLE
            itemDiscoverPlantsTitle.text = plants[position].name

            val selectPlantsTextView =holder.itemView.findViewById<TextView>(R.id.itemDiscoverPlantsTitle)
            if(selectPlantsTextView!=null){
                selectPlantsTextView.text = plantFilterList[position].name
                Glide.with(context).load(plantFilterList[position].imageUrl).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(itemDiscoverPlantsImage)
                activePlantInfo=plantFilterList[position].category +"_"+plantFilterList[position].name
            }


            //SET NAVIGATION
            itemDiscoverPlantsLayout.setOnClickListener {

                var bundleActivePlant= bundleOf(Pair("activePlantInfo",activePlantInfo))
                Navigation.findNavController(itemDiscoverPlantsImage).navigate(R.id.discoverPlantDetailFragment,bundleActivePlant)
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
                    val resultList = mutableListOf<PlantsInfo>()
                    for (row in plants) {
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    plantFilterList = resultList
                }
                Log.d("ListaFiltrata",plantFilterList.toString())

                val filterResult = FilterResults()
                filterResult.values = plantFilterList
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                plantFilterList = p1?.values as List<PlantsInfo>
                notifyDataSetChanged()
            }

        }

    }
}

