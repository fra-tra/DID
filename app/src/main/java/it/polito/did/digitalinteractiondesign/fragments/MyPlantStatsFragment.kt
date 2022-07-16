package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant
import org.w3c.dom.Text
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantStatsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantStatsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_plant_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TEST PLANT
        val plant = Plant("Basilico", null, false, 60.0)

        // set tv measure
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




        //TEST
        pbWater.progress = plant.waterMeasure.roundToInt()
        pbTemperature.progress = 95
        pbBrightness.progress = 12
        showMeasureAlert(pbWater, icAlertWater, icAlertOutlineWater, 10, 20, 80, 90)
        showMeasureAlert(pbTemperature, icAlertTemperature, icAlertOutlineTemperature, 10, 20, 80, 90)
        showMeasureAlert(pbBrightness, icAlertBrightness, icAlertOutlineBrightness, 10, 20, 80, 90)

        tvWaterMeasure.text = pbWater.progress.toString()
        tvTemperatureMeasure.text = pbTemperature.progress.toString()
        tvBrightnessMeasure.text = pbBrightness.progress.toString()
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