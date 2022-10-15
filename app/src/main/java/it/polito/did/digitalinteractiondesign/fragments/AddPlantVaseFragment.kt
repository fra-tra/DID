package it.polito.did.digitalinteractiondesign.fragments

import android.media.Image
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.FragmentAddPlantVaseBinding
import it.polito.did.digitalinteractiondesign.databinding.FragmentMyPlantSettingsVaseBinding
import it.polito.did.digitalinteractiondesign.structures.Plant
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPlantVaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPlantVaseFragment : Fragment() {

    companion object{
        var namePlant :String="Name"
            get() {return field}
            set(value) {field=value}
        var imagePlant : String=""
            get() {return field}
            set(value) {field=value}
        var roomPlant : String = ""
            get() {return field}
            set(value) {field=value}
        var vaseTypePlant:String =""
            get() {return field}
            set(value) {field=value}
        var vaseSizePlant:String =""
            get() {return field}
            set(value) {field=value}
        var soilTypePlant:String=""
            get() {return field}
            set(value) {field=value}
        var informationPlant:String=""
            get() {return field}
            set(value) {field=value}
        var tipsPlant:String=""
            get() {return field}
            set(value) {field=value}
        var category:String=""
            get() {return field}
            set(value) {field=value}
        var dayBorned:String=""
            get() {return field}
            set(value) {field=value}


        var plantToAdd=Plant(namePlant, imagePlant, roomPlant, vaseTypePlant, vaseSizePlant,soilTypePlant,informationPlant,tipsPlant,category,
            dayBorned)




        fun refreshPlant(){
            namePlant="Name"
            imagePlant="imageMissing"
            roomPlant=""
            vaseTypePlant=""
            vaseSizePlant=""
            soilTypePlant=""
            tipsPlant=""
            informationPlant=""
            category=""
        }

    }
    private var _binding: FragmentAddPlantVaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPlantVaseBinding.inflate(inflater, container, false )
        val vaseType = resources.getStringArray(R.array.vase_type)
        val vaseTypeArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_vase_settings, vaseType)
        binding.textViewVaseType.setAdapter(vaseTypeArrayAdapter)

        val vaseSize = resources.getStringArray(R.array.vase_size)
        val vaseSizeArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_vase_settings, vaseSize)
        binding.textViewVaseSize.setAdapter(vaseSizeArrayAdapter)

        val soilType = resources.getStringArray(R.array.soil_type)
        val soilTypeArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_vase_settings, soilType)
        binding.textViewSoilType.setAdapter(soilTypeArrayAdapter)
        Log.d("Info", informationPlant)
        Log.d("tips", tipsPlant)

        binding.textViewVaseType.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val selectedValue: String? = vaseTypeArrayAdapter.getItem(position)
                if (selectedValue != null) {
                    Log.d("Selected vase type: ", selectedValue)
                    vaseTypePlant=selectedValue
                }
            }
        binding.textViewVaseSize.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val selectedValue: String? = vaseSizeArrayAdapter.getItem(position)
                if (selectedValue != null) {
                    Log.d("Selected vase size: ", selectedValue)
                    vaseSizePlant=selectedValue
                }
            }
        binding.textViewSoilType.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val selectedValue: String? = soilTypeArrayAdapter.getItem(position)
                if (selectedValue != null) {
                    Log.d("Selected soil type: ", selectedValue)
                    soilTypePlant=selectedValue
                }
            }

                return binding.root
            }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var backBtn = view.findViewById<ImageButton>(R.id.backButtonAddPlantVase)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        var btnAddPlantFinal = view.findViewById<Button>(R.id.btnAddPlantFinal)
        btnAddPlantFinal.setOnClickListener {
            var tempdata = LocalDateTime.now().toString().split(".")[0]
            plantToAdd=Plant(namePlant, imagePlant, roomPlant, vaseTypePlant, vaseSizePlant,soilTypePlant, informationPlant,tipsPlant,category, tempdata)
            //Log.d("category", plantToAdd.categoryPlant.toString())
            ManagerFirebase.addPlantInDB("Alive", plantToAdd)
            var bundleActivePlant= bundleOf(Pair("activePlant", category + "_" + tempdata))
            refreshPlant()

            findNavController().navigate(R.id.action_addPlantVaseFragment_to_loadingAddPlantFragment,bundleActivePlant)
        }

    }
}