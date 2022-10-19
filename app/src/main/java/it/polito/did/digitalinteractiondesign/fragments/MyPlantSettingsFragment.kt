package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantSettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantSettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_plant_settings, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPlantSettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPlantSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  val confirmPlantDeath = AlertDialog.Builder(activity)
            .setMessage("Are you sure you want to register YOUR PLANT's death?")
            .setPositiveButton("R.I.P") { _, _ ->
                findNavController().navigate(R.id.action_myPlantSettingsFragment_to_loadingPlantFuneralFragment)
            }
            .setNegativeButton("Cancel") { _, _ ->

            }.create() */

        var btnBack = view.findViewById<ImageButton>(R.id.backButtonPlantSettings)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }



        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            // ci serve sapere la pianta schiacciata
            var activePlantID = arguments?.get("activePlant")

            //Log.d("IdActivePlant", activePlantID.toString())
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null)  activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)

           if(activePlant!=null){
                var btnName = view.findViewById<TextView>(R.id.plantNameSettings)
                btnName.text=activePlant.name + ">"
                btnName.setOnClickListener {
                    var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
                    findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsNameFragment,bundleActivePlant)
                }

                var btnRoom = view.findViewById<TextView>(R.id.roomNameSettings)
                btnRoom.text=activePlant.room + ">"
                btnRoom.setOnClickListener {
                    var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
                    findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsRoomFragment,bundleActivePlant)
                }

                var btnVaseTypeSettings = view.findViewById<TextView>(R.id.vaseTypeSettings)
                btnVaseTypeSettings.text=activePlant.vaseType + ">"
                btnVaseTypeSettings.setOnClickListener {
                    var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
                    findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsVaseFragment,bundleActivePlant)
                }
                var btnVaseSizeSettings = view.findViewById<TextView>(R.id.vaseSizeSettings)
                btnVaseSizeSettings.text=activePlant.vaseSize + ">"
                btnVaseSizeSettings.setOnClickListener {
                    var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
                    findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsVaseFragment,bundleActivePlant)
                }
                var btnSoilTypeSettings = view.findViewById<TextView>(R.id.soilTypeSettings)
                btnSoilTypeSettings.text=activePlant.soilType + ">"
                btnSoilTypeSettings.setOnClickListener {
                    var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
                    findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsVaseFragment,bundleActivePlant)
                }
               var btnDeathPlant = view.findViewById<Button>(R.id.btnDeathPlant)
               btnDeathPlant.setOnClickListener {
                   // confirmPlantDeath.show()
                   var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
                   val fragmentManager = childFragmentManager
                   val newFragment = CustomDeathDialog()
                   newFragment.setArguments(bundleActivePlant)
                   newFragment.show(fragmentManager, "dialog")
               }


            }




        })

        /* var btnDeletePlant = view.findViewById<Button>(R.id.btnDeletePlant)
        btnDeletePlant.setOnClickListener {
            // if button is already in selected state and now it is pressed
            // again,then it will reach in not selected state and vice versa
            btnDeletePlant.isSelected != btnDeletePlant.isSelected
        }*/


    }
}