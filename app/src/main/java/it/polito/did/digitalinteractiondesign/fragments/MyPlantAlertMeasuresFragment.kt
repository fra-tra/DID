package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import it.polito.did.digitalinteractiondesign.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantAlertMeasuresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantAlertMeasuresFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_plant_alert_measures, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var messageWater = view.findViewById<TextView>(R.id.alertMessageWater)
        var messageTemperature = view.findViewById<TextView>(R.id.alertMessageTemperature)
        var messageBrightness = view.findViewById<TextView>(R.id.alertMessageBrightness)

        showMeasureAlert(messageWater, 12, 5, 10, 80, 90)
        showMeasureAlert(messageTemperature, 10, 60, 10, 80, 90)
        showMeasureAlert(messageBrightness, 85, 5, 10, 80, 90)
    }

    //FUNCTION TO BE IMPROVED AND IMPLEMENTED: CHECK IF WATER/BRIGHTNESS/TEMPERATURE MEASURE IS IN DANGER OR WARNING STATE
    //IN VIEW MODEL AND SET THE TEXT ACCORDINGLY
    private fun showMeasureAlert(message: TextView, measure: Long,
                                 minMeasureDanger: Int, minMeasureWarning: Int, maxMeasureWarning: Int, maxMeasureDanger: Int) {

        if(measure <= minMeasureDanger) {
            message.visibility = View.VISIBLE
            message.setBackgroundResource(R.drawable.red_background)
            message.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            message.text = "DANGER"
        }
        else if (measure <= minMeasureWarning) {
            message.visibility = View.VISIBLE
            message.setBackgroundResource(R.drawable.yellow_background)
            message.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark_grey))
            message.text = "WARNING"
        }
        else if (measure >= maxMeasureDanger) {
            message.visibility = View.VISIBLE
            message.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            message.setBackgroundResource(R.drawable.red_background)
            message.text = "DANGER"

        }
        else if (measure >= maxMeasureWarning) {
            message.visibility = View.VISIBLE
            message.setBackgroundResource(R.drawable.yellow_background)
            message.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark_grey))
            message.text = "WARNING"
        }
        else {
            message.visibility = View.GONE
        }


    }

}