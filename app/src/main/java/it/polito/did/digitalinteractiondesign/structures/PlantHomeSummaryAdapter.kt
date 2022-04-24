package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantSummaryHomeBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomCardBinding
import kotlin.math.roundToInt

class PlantHomeSummaryAdapter(val plants: List <Plant>): RecyclerView.Adapter<PlantHomeSummaryAdapter.PlantHomeSummaryViewHolder>() {
    inner class PlantHomeSummaryViewHolder(val binding: ItemPlantSummaryHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHomeSummaryViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlantSummaryHomeBinding.inflate(layoutInflater, parent, false)
        return PlantHomeSummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantHomeSummaryViewHolder, position: Int) {
        holder.binding.apply {
            // SET IMAGE


            // SET MEASURES
            progressBarWaterHome.progress = plants[position].waterMeasure.roundToInt()

            //SET MEASURES ALERT
            //TESTING HUMIDITY - TEMPERATURE AND BRIGHTNESS STILL TO BE IMPLEMENTED
            showMeasureAlert(
                progressBarWaterHome, alertWater,
                plants[position].waterMeasuresReferences[0].roundToInt(),
                plants[position].waterMeasuresReferences[1].roundToInt(),
                plants[position].waterMeasuresReferences[2].roundToInt(),
                plants[position].waterMeasuresReferences[3].roundToInt()
            )

            //SET TITLE
            myPlantTitleHome.text = plants[position].name

            //SET NAVIGATION TO PLANT DETAIL
            seePlantDetailHome.setOnClickListener {
                Log.d("Show", "")

                val bottomNav: BottomNavigationView = (context as Home_Activity).findViewById(R.id.bottomNavigationView)
                bottomNav.selectedItemId = R.id.piante
                Navigation.findNavController(seePlantDetailHome).navigate(R.id.myPlantFragment)
            }
        }
    }

    override fun getItemCount(): Int {
        return plants.size
    }
}

    private fun showMeasureAlert(progressBar: ProgressBar, imageToShow: ImageView, minMeasureDanger: Int, minMeasureWarning: Int,  maxMeasureWarning: Int, maxMeasureDanger: Int) {

        if(progressBar.progress <= minMeasureDanger) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_danger)
        }
        else if (progressBar.progress <= minMeasureWarning) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_warning)
        }
        else if (progressBar.progress >= maxMeasureDanger) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_danger)
        }
        else if (progressBar.progress >= maxMeasureWarning) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_warning)
        }
        else {
            imageToShow.isVisible = false
        }


    }
