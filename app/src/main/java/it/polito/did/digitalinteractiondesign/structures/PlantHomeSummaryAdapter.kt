package it.polito.did.digitalinteractiondesign.structures

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.databinding.ItemPlantSummaryHomeBinding
import it.polito.did.digitalinteractiondesign.databinding.ItemRoomCardBinding
import kotlin.math.roundToInt

class PlantHomeSummaryAdapter(val plants: MutableList<Plant>): RecyclerView.Adapter<PlantHomeSummaryAdapter.PlantHomeSummaryViewHolder>() {
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
            Glide.with(holder.itemView).load(plants[position].image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(plantImageHome)

            // SET (water) MEASURES
            progressBarWaterHome.progress = plants[position].humidity.roundToInt()
            Log.d("Humidity Level Home", plants[position].humidity.toString())


            //set temperature and brightness default measure (equal for every plant)
            progressBarTemperatureHome.progress = plants[position].temperature // temperatura pianta
            progressBarBrightnessHome.progress = plants[position].brightness// illuminazione della pianta

            //SET MEASURES ALERT
            //SHOW ALERT FOR HUMIDITY - TEST
            showMeasureAlert(
                progressBarWaterHome, alertWater, 15, 20, 65, 70
            )

            //SHOW ALERT FOR BRIGHTNESS AND TEMPERATURE - DEFAULT
            showMeasureAlert(
                progressBarTemperatureHome, alertTemperature,
                0, 5, 40, 45
            )

            showMeasureAlert(
                progressBarBrightnessHome, alertBrightness,
                5, 10, 90, 95
            )

            //SET TITLE
            myPlantTitleHome.text = plants[position].name

            //SET NAVIGATION TO PLANT DETAIL
            seePlantDetailHome.setOnClickListener {
                Log.d("Show", "")
                ManagerPlants.assignActivePlant(plants[position].idIdentification)
                val bottomNav: BottomNavigationView = (context as Home_Activity).findViewById(R.id.bottomNavigationView)
                bottomNav.selectedItemId = R.id.piante
                var bundleActivePlant= bundleOf(Pair("activePlant",plants[position].idIdentification))
                Navigation.findNavController(seePlantDetailHome).navigate(R.id.myPlantFragment,bundleActivePlant)
            }
        }
    }

    override fun getItemCount(): Int {
        return plants.size
    }
}

//FUNCTION TO BE IMPROVED WHEN VIEW MODEL IS IMPLEMENTED: CHECK IN VIEW MODEL IF THE MEASURE IS TO BE ALERTED (WARNING OR DANGER)
//SHOW MEASURE ALERT IF FLAG FOR THE PLANT IS RISEN
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
