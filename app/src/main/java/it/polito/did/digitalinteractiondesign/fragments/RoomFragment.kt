package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
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




class RoomFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activeRoomName = arguments?.get("activeRoom")

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")

       // Log.d("ActiveRoom",activeRoomName.toString())

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            var roomTitle = view.findViewById<TextView>(R.id.roomTitle)
            roomTitle.text=activeRoomName.toString()
            val plantInThatRoomTempList= ListPlants()
            for((key,value)in it){
                    val mapTemp : HashMap<String,Any?> = value as HashMap<String, Any?>
                    var tempPlant = ManagerFirebase.fromHashMapToPlant(mapTemp)
                     if(mapTemp!=null && tempPlant.room==activeRoomName) {
                         //android.util.Log.i("ListPlants","="+tempPlant.toString())
                         plantInThatRoomTempList.addPlantInList(tempPlant)
                     }

            }
            val adapter = PlantCardListAdapter(plantInThatRoomTempList.listPlants)
            val rvPlants = view.findViewById<RecyclerView>(R.id.rvRoomPlants)
            rvPlants.adapter = adapter
            rvPlants.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        })


        var backBtn = view.findViewById<ImageButton>(R.id.backButtonRoom)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}