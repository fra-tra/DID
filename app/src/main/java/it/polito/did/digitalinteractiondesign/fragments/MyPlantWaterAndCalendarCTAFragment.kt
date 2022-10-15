package it.polito.did.digitalinteractiondesign.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.structures.Plant
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantWaterAndCalendarCTAFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantWaterAndCalendarCTAFragment : Fragment() {
    companion object{
        var activePlantID=""
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?




    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(
            R.layout.fragment_my_plant_water_and_calendar_c_t_a,
            container,
            false


        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        var btnCalendarPlant = view.findViewById<ImageButton>(R.id.btnCalendarPlant)
        btnCalendarPlant.setOnClickListener {
           // findNavController().navigate(R.id.action_myPlantFragment_to_loadingWaterPlantFragment)
            val bottomNav: BottomNavigationView = (context as Home_Activity).findViewById(R.id.bottomNavigationView)
            bottomNav.selectedItemId = R.id.calendarizzazione
            findNavController().navigate(R.id.calendarizzazione)
        }

        //seek bar luminosità
        var seekBarBrightness = view.findViewById<SeekBar>(R.id.seekBarBrightness)
        //il valore selezionato si accede tramite seekBarBrightness.progress

        // prendo i dati da questo percorso
        var tvLastWatered = view.findViewById<TextView>(R.id.tvLastWatered)
        viewModelDB.getPlantsFromDBRealtime("Alive")


        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            var bundleActivePlant= bundleOf(Pair("activePlant", Companion.activePlantID))
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null){
                activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            }

            if(activePlant!=null){

                tvLastWatered.text=activePlant.lastWateredDate.split("T")[0]

                var btnWaterPlant = view.findViewById<Button>(R.id.btnWaterPlant)
                btnWaterPlant.setOnClickListener {
                    ManagerFirebase.updateValuePlantAlive(Companion.activePlantID,"Times Wetted","${activePlant?.timesWetted?.plus(1)}")
                    ManagerFirebase.updateValuePlantAlive(Companion.activePlantID,"Last Watered Date",LocalDateTime.now().toString().split(".")[0])
                    ManagerFirebase.updateValuePlantAlive(Companion.activePlantID,"Pump Water","true")
                    findNavController().navigate(R.id.action_myPlantFragment_to_loadingWaterPlantFragment,bundleActivePlant)
                    //BAGNA LE PIANTE SPEDISCI DATO NEL DATABASE - PUMP ON
                }
            /*
                //var btnLight = view.findViewById<SeekBar>(R.id.)
                btnLigt.setOnClickListener {
                    ManagerFirebase.updateValuePlantAlive(Companion.activePlantID,"Light","${!(activePlant.light)}")
                }
                */
            }





        })


    }



}