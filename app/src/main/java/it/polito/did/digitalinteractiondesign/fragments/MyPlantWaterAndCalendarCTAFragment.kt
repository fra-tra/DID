package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.R

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
    }

}