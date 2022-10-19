package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant
import it.polito.did.digitalinteractiondesign.structures.Room
import it.polito.did.digitalinteractiondesign.structures.RoomImageAdapter
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantSettingsRoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantSettingsRoomFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_my_plant_settings_room, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPlantSettingsRoomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPlantSettingsRoomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        var activePlantID = arguments?.get("activePlant")

        //TEST ROOM LIST
        val rooms = arrayListOf(
            Room("Kitchen"), Room ("Living Room"), Room("Balcony"),Room ("Bedroom"), Room("Bathroom"),Room("Garden"),Room("Dining Room")

        )

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            //salto le verifiche delle variabili

            //Log.d("IdActivePlant", activePlantID.toString())
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null)  activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            if(activePlant!=null){

                val adapter = RoomImageAdapter(rooms)
                var iteratorRoom=0
                for (room in adapter.rooms){

                    if (room.name==activePlant.room){
                        adapter.selectedItemPos=iteratorRoom
                        adapter.lastItemSelectedPos=iteratorRoom
                        adapter.activePlantID=activePlant.idIdentification
                    }
                    iteratorRoom+=1;
                }
                val rv = view.findViewById<RecyclerView>(R.id.rvRoomImages)

                rv.adapter = adapter

                // rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                // val layoutManager = GridLayoutManager(activity, 2)
                val layoutManager = GridLayoutManager(activity, 2)
                rv.layoutManager = layoutManager
                (rv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false


            }


        })


        val btnBack = view.findViewById<ImageButton>(R.id.backButtonRoomSettings)
        btnBack.setOnClickListener {
           // ManagerFirebase.updateValuePlantAlive(activePlantID.toString(),"Room",tempRoomForChange.toString())

            findNavController().navigateUp()
        }

     /*   var btnAddRoom = view.findViewById<ImageView>(R.id.btnAddRoom)
        btnAddRoom.setOnClickListener {

            findNavController().navigate(R.id.action_myPlantSettingsRoomFragment_to_addRoomFragment)
        }*/

    }

}