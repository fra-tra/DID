package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant
import org.w3c.dom.Text
import java.time.LocalDateTime
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantStatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantStatsFragment : Fragment() {
    companion object{
        var activePlantID=""
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_plant_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // recupero viste
        var tvWaterMeasure = view.findViewById<TextView>(R.id.tvWaterMeasure)
        var pbWater = view.findViewById<ProgressBar>(R.id.pbWater)
        var icAlertWater = view.findViewById<ImageView>(R.id.icAlertWater)
        var icAlertOutlineWater = view.findViewById<ImageView>(R.id.icAlertOutlineWater)
        var alertMessageWater = view.findViewById<TextView>(R.id.alertMessageWater)



        var tvTemperatureMeasure = view.findViewById<TextView>(R.id.tvTemperatureMeasure)
        var pbTemperature = view.findViewById<ProgressBar>(R.id.pbTemperature)
        var icAlertTemperature = view.findViewById<ImageView>(R.id.icAlertTemperature)
        var icAlertOutlineTemperature = view.findViewById<ImageView>(R.id.icAlertOutlineTemperature)
        var alertMessageTemperature = view.findViewById<TextView>(R.id.alertMessageTemperature)



        var tvBrightnessMeasure = view.findViewById<TextView>(R.id.tvBrightnessMeasure)
        var pbBrightness = view.findViewById<ProgressBar>(R.id.pbBrightness)
        var icAlertBrightness = view.findViewById<ImageView>(R.id.icAlertBrightness)
        var icAlertOutlineBrightness = view.findViewById<ImageView>(R.id.icAlertOutlineBrightness)
        var alertMessageBrightness = view.findViewById<TextView>(R.id.alertMessageBrightness)

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")


        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null){
                activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
                if(activePlant!=null){
                    pbWater.progress = activePlant.humidity.roundToInt()
                    tvWaterMeasure.text = pbWater.progress.toString()
                    pbTemperature.progress = activePlant.temperature
                    tvTemperatureMeasure.text = pbTemperature.progress.toString() + "°C"
                    pbBrightness.progress = activePlant.brightness
                    tvBrightnessMeasure.text = pbBrightness.progress.toString() + "%"


                }
            }









        })

        //TEST PLANT
        val plant = Plant("Basilico", "imageMissing")






        //TEST


        //show alert for water -> rendere dinamico in base ai dati
        showMeasureAlert(pbWater, icAlertWater, icAlertOutlineWater, 10, 20, 80, 90)

        //show alert for temperature -> DEFAULT RESTA COSI
        showMeasureAlert(pbTemperature, icAlertTemperature, icAlertOutlineTemperature, 10, 20, 30, 40)

        //show alert for brightness -> DEFAULT RESTA COSI
        showMeasureAlert(pbBrightness, icAlertBrightness, icAlertOutlineBrightness, 10, 20, 80, 90)


    }

    //FUNCTION TO BE IMPROVED WHEN VIEW MODEL IS IMPLEMENTED: CHECK IN VIEW MODEL IF THE MEASURE IS TO BE ALERTED (WARNING OR DANGER)
    //SHOW MEASURE ALERT IF FLAG FOR THE PLANT IS RISEN
    private fun showMeasureAlert(progressBar: ProgressBar, imageToShow: ImageView, outlineToShow:ImageView,
                                 minMeasureDanger: Int, minMeasureWarning: Int, maxMeasureWarning: Int, maxMeasureDanger: Int) {

        if(progressBar.progress <= minMeasureDanger) {
            imageToShow.visibility = View.VISIBLE
            imageToShow.setImageResource(R.drawable.ic_danger)
            outlineToShow.visibility = View.VISIBLE
            outlineToShow.setImageResource(R.drawable.red_outline)

        }
        else if (progressBar.progress <= minMeasureWarning) {
            imageToShow.visibility = View.VISIBLE
            imageToShow.setImageResource(R.drawable.ic_warning)
            outlineToShow.visibility = View.VISIBLE
            outlineToShow.setImageResource(R.drawable.yellow_outline)
        }
        else if (progressBar.progress >= maxMeasureDanger) {
            imageToShow.visibility = View.VISIBLE
            imageToShow.setImageResource(R.drawable.ic_danger)
            outlineToShow.visibility = View.VISIBLE
            outlineToShow.setImageResource(R.drawable.red_outline)
        }
        else if (progressBar.progress >= maxMeasureWarning) {
            imageToShow.visibility = View.VISIBLE
            imageToShow.setImageResource(R.drawable.ic_warning)
            outlineToShow.visibility = View.VISIBLE
            outlineToShow.setImageResource(R.drawable.yellow_outline)
        }
        else {
            imageToShow.visibility = View.INVISIBLE
            outlineToShow.visibility = View.INVISIBLE
        }


    }

}