package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.FragmentMyPlantSettingsVaseBinding
import it.polito.did.digitalinteractiondesign.structures.Plant

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantSettingsVaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantSettingsVaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
  private var _binding: FragmentMyPlantSettingsVaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyPlantSettingsVaseBinding.inflate(inflater, container, false )
        val vaseType = resources.getStringArray(R.array.vase_type)
        val vaseTypeArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_vase_settings, vaseType)
        binding.textViewVaseType.setAdapter(vaseTypeArrayAdapter)

        val vaseSize = resources.getStringArray(R.array.vase_size)
        val vaseSizeArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_vase_settings, vaseSize)
        binding.textViewVaseSize.setAdapter(vaseSizeArrayAdapter)

        val soilType = resources.getStringArray(R.array.soil_type)
        val soilTypeArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_vase_settings, soilType)
        binding.textViewSoilType.setAdapter(soilTypeArrayAdapter)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            //salto le verifiche delle variabili

            var activePlantID = arguments?.get("activePlant")

            Log.d("IdActivePlant", activePlantID.toString())
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null)  activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            if(activePlant!=null){


            }
            var btnBack = view.findViewById<ImageButton>(R.id.backButtonVaseSettings)
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

        })

    }
}