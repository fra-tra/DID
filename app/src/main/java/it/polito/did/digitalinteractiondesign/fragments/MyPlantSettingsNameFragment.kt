package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantSettingsNameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantSettingsNameFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_my_plant_settings_name, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPlantSettingsNameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPlantSettingsNameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activePlantNameArray = arguments?.get("activePlantName")
        var namePlant=""
        var idPlant=""
        var textNamePlant=view.findViewById<EditText>(R.id.editTextTPlantName)
        var btnBack = view.findViewById<ImageButton>(R.id.backButtonNameSettings)

        val bundle = this.arguments
        if (bundle != null) {
            namePlant = bundle.getString("name").toString()
            idPlant=bundle.getString("id").toString()
            textNamePlant.setText(namePlant.toString())
            btnBack.setOnClickListener {

                ManagerFirebase.updateValuePlantAlive(idPlant.toString(),"Name",textNamePlant.text.toString())
                findNavController().navigateUp()
            }

        }
        Log.d("TestID", idPlant)












    }


}