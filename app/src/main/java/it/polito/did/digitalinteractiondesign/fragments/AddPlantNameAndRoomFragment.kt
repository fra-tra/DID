package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import it.polito.did.digitalinteractiondesign.ListPlantsInfo
import it.polito.did.digitalinteractiondesign.ManagerPlantsInfoFirestore
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.DiscoverAddPlantInRoomAdapter
import it.polito.did.digitalinteractiondesign.structures.PlantsInfo
import it.polito.did.digitalinteractiondesign.structures.Room
import it.polito.did.digitalinteractiondesign.structures.RoomImageAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPlantNameAndRoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPlantNameAndRoomFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_add_plant_name_and_room, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPlantNameAndRoomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPlantNameAndRoomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activePlantInfoID = arguments?.get("activePlantInfo").toString()
        var listCategoryName=activePlantInfoID.split("_")

        // aggiungo info della pianta in base alla categoria ed il nome selezionato
        var plantInfoTemporanea = ManagerPlantsInfoFirestore.returnPlantByCategoryByName(listCategoryName[0],listCategoryName[1])
        AddPlantVaseFragment.informationPlant=plantInfoTemporanea.description
        AddPlantVaseFragment.tipsPlant=plantInfoTemporanea.tips
        AddPlantVaseFragment.category=plantInfoTemporanea.category
        AddPlantVaseFragment.imagePlant=plantInfoTemporanea.imageUrl


        var backBtn = view.findViewById<ImageButton>(R.id.backButtonAddPlantNameAndRoom)
        var namePlant=view.findViewById<EditText>(R.id.editTextTPlantName)

        //Cambio il nome nella classe DiscoverAddPlantInRoomAdapter
        //è inizializzato a Name sempre
        namePlant.addTextChangedListener { text ->
            //Log.d("namePlant","${text.toString()}")
            AddPlantVaseFragment.namePlant=text.toString()
        }

        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        val rooms = arrayListOf(
            Room("Kitchen"), Room ("Living Room"), Room("Balcony"),Room ("Bedroom"), Room("Bathroom"),Room("Garden"),Room("Dining Room")

        )

        val adapter = DiscoverAddPlantInRoomAdapter(rooms)
        val rv = view.findViewById<RecyclerView>(R.id.rvRoomImages)
        rv.adapter = adapter
        val layoutManager = GridLayoutManager(activity, 2)
        rv.layoutManager = layoutManager
        (rv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false



      /*  var btnAddRoom = view.findViewById<ImageView>(R.id.btnAddRoom)
        btnAddRoom.setOnClickListener {
            findNavController().navigate(R.id.action_addPlantNameAndRoomFragment_to_addRoomFragment)
        }*/

    }

}