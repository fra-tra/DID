package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.ListPlants
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GraveyardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GraveyardFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_graveyard, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GraveyardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GraveyardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)

        viewModelDB.getPlantsFromDBRealtime("Dead")

        viewModelDB.returnListPlantsDied().observe(viewLifecycleOwner, Observer {
            val deadTempList= ListPlants()
            for((key,value) in it){
                // android.util.Log.i("HasMapPlants","$key=$value + ${it.values}")
                // android.util.Log.i("Plants","=$value ")
                val mapTemp : HashMap<String,Any?> = value as HashMap<String, Any?>
                if(mapTemp!=null){

                }
                var tempPlant = ManagerFirebase.fromHashMapToPlant(mapTemp)
                //android.util.Log.i("ListPlants","="+tempPlant.toString())
                deadTempList.addPlantInList(tempPlant)
            }
        Log.d("DOVESONO","Ho schiacciato ora")
        val adapter = PlantCardListAdapter(deadTempList.listPlants)
        val rvPlants = view.findViewById<RecyclerView>(R.id.rvGraveyardPlants)
        rvPlants.adapter = adapter
        rvPlants.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        var backBtn = view.findViewById<ImageButton>(R.id.backButtonGraveyard)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        })
}

}