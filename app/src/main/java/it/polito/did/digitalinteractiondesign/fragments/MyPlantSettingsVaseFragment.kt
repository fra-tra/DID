package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.fragment.app.Fragment
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

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var vaseTypePlant:String =""

        var vaseSizePlant:String =""

        var soilTypePlant:String=""


        var vaseType = resources.getStringArray(R.array.vase_type)
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
        binding.textViewVaseType.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val selectedValue: String? = vaseTypeArrayAdapter.getItem(position)
                if (selectedValue != null) {
                    Log.d("Selected vase type: ", selectedValue)
                    vaseTypePlant =selectedValue
                }
            }
        binding.textViewVaseSize.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val selectedValue: String? = vaseSizeArrayAdapter.getItem(position)
                if (selectedValue != null) {
                    Log.d("Selected vase size: ", selectedValue)
                   vaseSizePlant =selectedValue
                }
            }
        binding.textViewSoilType.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val selectedValue: String? = soilTypeArrayAdapter.getItem(position)
                if (selectedValue != null) {
                    Log.d("Selected soil type: ", selectedValue)
                    soilTypePlant =selectedValue
                }
            }

        viewModelDB.getPlantsFromDBRealtime("Alive")

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            //salto le verifiche delle variabili

            var activePlantID = arguments?.get("activePlant")

            Log.d("IdActivePlantVase", activePlantID.toString())
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null)  activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            if(activePlant!=null){
               // salvataggio sbagliato va bene solo in questa casistica, da ripensare per implementazioni
                   //ulteriori

                binding.textViewSoilType.setText("${activePlant.soilType}",false)
                binding.textViewVaseSize.setText("${activePlant.vaseSize}",false)
                binding.textViewVaseType.setText("${activePlant.vaseType}",false)


            }
            var btnBack = view.findViewById<ImageButton>(R.id.backButtonVaseSettings)
            btnBack.setOnClickListener {
                ManagerFirebase.updateValuePlantAlive(activePlantID.toString(),"Vase Size",vaseSizePlant)
                ManagerFirebase.updateValuePlantAlive(activePlantID.toString(),"Soil Type",soilTypePlant)
                ManagerFirebase.updateValuePlantAlive(activePlantID.toString(),"Vase Type",vaseTypePlant)
                findNavController().navigateUp()
            }

        })

    }
}