package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.R

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

        var btnBack = view.findViewById<Button>(R.id.backButtonPlantSettings)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        var btnDeathPlant = view.findViewById<Button>(R.id.btnDeathPlant)
        btnDeathPlant.setOnClickListener {
            // confirmPlantDeath.show()
            val fragmentManager = childFragmentManager
            val newFragment = CustomDeathDialog()
            newFragment.show(fragmentManager, "dialog")
        }

        /* var btnDeletePlant = view.findViewById<Button>(R.id.btnDeletePlant)
        btnDeletePlant.setOnClickListener {
            // if button is already in selected state and now it is pressed
            // again,then it will reach in not selected state and vice versa
            btnDeletePlant.isSelected != btnDeletePlant.isSelected
        }*/

        var btnName = view.findViewById<TextView>(R.id.plantNameSettings)
        btnName.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsNameFragment)
        }

        var btnRoom = view.findViewById<TextView>(R.id.roomNameSettings)
        btnRoom.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsRoomFragment)
        }

        var btnVaseTypeSettings = view.findViewById<TextView>(R.id.vaseTypeSettings)
        btnVaseTypeSettings.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsVaseFragment)
        }
        var btnVaseSizeSettings = view.findViewById<TextView>(R.id.vaseSizeSettings)
        btnVaseSizeSettings.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsVaseFragment)
        }
        var btnSoilTypeSettings = view.findViewById<TextView>(R.id.soilTypeSettings)
        btnSoilTypeSettings.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantSettingsFragment_to_myPlantSettingsVaseFragment)
        }
    }
}