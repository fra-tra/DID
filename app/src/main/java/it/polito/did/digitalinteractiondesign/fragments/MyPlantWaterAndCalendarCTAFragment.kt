package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var btnWaterPlant = view.findViewById<Button>(R.id.btnWaterPlant)
        btnWaterPlant.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantFragment_to_loadingWaterPlantFragment)
        }

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
    }

}