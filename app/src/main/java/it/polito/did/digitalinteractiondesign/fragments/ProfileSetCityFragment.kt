package it.polito.did.digitalinteractiondesign.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import it.polito.did.digitalinteractiondesign.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileSetCityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileSetCityFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_profile_set_city, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileSetCityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileSetCityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var backBtn = view.findViewById<ImageButton>(R.id.backButtonSetCity)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


        val countries = ArrayList<String>()
        countries.add("Italy")

        var tvCountry = view.findViewById<AutoCompleteTextView>(R.id.tvCountry)
        val countryArrayAdapter = ArrayAdapter(requireActivity(), R.layout.item_dropdown_vase_settings, countries)
        tvCountry.setAdapter(countryArrayAdapter)
        var tvCity = view.findViewById<EditText>(R.id.tvCity)


        //SET HERE COUNTRY AND CITY
        tvCountry.setText("Italy",false)
        tvCity.setText("Turin")


    }
}