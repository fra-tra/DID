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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant
import kotlin.math.roundToInt

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

        //VALORI CHE INDICANO SE LA MISURA È IN STATO DI ALLERTA
        //0 = NO ALERT, 1 = Danger valore troppo basso (N), 2 = Warning valore troppo basso (N), 3 = Warning valore troppo alto (P), 4 = Danger, valore troppo alto(P)
        //il valore viene stablito dalla funzione showMeasureAlert
        var isToAlertT : Int
        var isToAlertW : Int
        var isToAlertB : Int


        var messageWater = view.findViewById<TextView>(R.id.alertMessageWater)
        var messageTemperature = view.findViewById<TextView>(R.id.alertMessageTemperature)
        var messageBrightness = view.findViewById<TextView>(R.id.alertMessageBrightness)

      // isToAlertW = showMeasureAlert(messageWater, 12, 5, 10, 80, 90)
      // isToAlertT = showMeasureAlert(messageTemperature, 32, 10, 20, 30, 40)
      // isToAlertB=  showMeasureAlert(messageBrightness, 20, 10, 20, 80, 90)

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            var tempPlant = it.get(MyPlantStatsFragment.activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null){
                activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
                if(activePlant!=null){
                    var humidityMeasure = activePlant.humidity.toLong()
                    isToAlertW = showMeasureAlert(messageWater, humidityMeasure, 15, 20, 65, 70)
                    //per ogni misura (water, temperature, brightness) si setta il testo in base allo stato di allerta (danger negativo/positivo o warning negativo/positivo)
                    if (isToAlertW == 1) {
                        messageWater.setText(R.string.MyPlantAlert_Water_Danger_N);
                    }
                    else if (isToAlertW == 2) {
                        messageWater.setText(R.string.MyPlantAlert_Water_Warning_N);
                    }
                    else if (isToAlertW == 3) {
                        messageWater.setText(R.string.MyPlantAlert_Water_Warning_P);
                    }
                    else if (isToAlertW == 4) {
                        messageWater.setText(R.string.MyPlantAlert_Water_Danger_P);
                    }

                    var temperatureMeasure = activePlant.temperature.toLong()
                    isToAlertT = showMeasureAlert(messageTemperature, temperatureMeasure, 0, 5, 40, 45)

                    if (isToAlertT == 1) {
                        messageTemperature.setText(R.string.MyPlantAlert_Temperature_Danger_N);
                    }
                    else if (isToAlertT == 2) {
                        messageTemperature.setText(R.string.MyPlantAlert_Temperature_Warning_N);
                    }
                    else if (isToAlertT == 3) {
                        messageTemperature.setText(R.string.MyPlantAlert_Temperature_Warning_P);
                    }
                    else if (isToAlertT == 4) {
                        messageTemperature.setText(R.string.MyPlantAlert_Temperature_Danger_P);
                    }

                    var brightnessMeasure = activePlant.brightness.toLong()
                    isToAlertB=  showMeasureAlert(messageBrightness, brightnessMeasure, 5, 10, 90, 95)

                    if (isToAlertB == 1) {
                        messageBrightness.setText(R.string.MyPlantAlert_Brightness_Danger_N);
                    }
                    else if (isToAlertB == 2) {
                        messageBrightness.setText(R.string.MyPlantAlert_Brightness_Warning_N);
                    }
                    else if (isToAlertB == 3) {
                        messageBrightness.setText(R.string.MyPlantAlert_Brightness_Warning_P);
                    }
                    else if (isToAlertB == 4) {
                        messageBrightness.setText(R.string.MyPlantAlert_Brightness_Danger_P);
                    }

                }
            }









        })







    }

    //FUNCTION TO BE IMPROVED AND IMPLEMENTED: CHECK IF WATER/BRIGHTNESS/TEMPERATURE MEASURE IS IN DANGER OR WARNING STATE
    //IN VIEW MODEL AND SET THE TEXT ACCORDINGLY
    private fun showMeasureAlert(message: TextView,  measure: Long,
                                 minMeasureDanger: Int, minMeasureWarning: Int, maxMeasureWarning: Int, maxMeasureDanger: Int): Int {

        if(measure <= minMeasureDanger) {
            message.visibility = View.VISIBLE
            message.setBackgroundResource(R.drawable.red_background)
            message.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
          //  message.text = "DANGER"
            return 1;
        }
        else if (measure <= minMeasureWarning) {
            message.visibility = View.VISIBLE
            message.setBackgroundResource(R.drawable.yellow_background)
            message.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark_grey))
          //  message.text = "WARNING"
            return 2;
        }
        else if (measure >= maxMeasureDanger) {
            message.visibility = View.VISIBLE
            message.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            message.setBackgroundResource(R.drawable.red_background)
         //   message.text = "DANGER"
            return 4;

        }
        else if (measure >= maxMeasureWarning) {
            message.visibility = View.VISIBLE
            message.setBackgroundResource(R.drawable.yellow_background)
            message.setTextColor(ContextCompat.getColor(requireActivity(), R.color.dark_grey))
            return 3;
          //  message.text = "WARNING"
        }
        else {
            message.visibility = View.GONE
            return 0;
        }


    }

}