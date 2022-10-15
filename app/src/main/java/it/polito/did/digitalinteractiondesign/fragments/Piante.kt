package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
 * Use the [Piante.newInstance] factory method to
 * create an instance of this fragment.
 */
class Piante : Fragment() {
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
        //Hide action bar from fragment
        // does it need to be specified in onstart and onresume too?
       // (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_piante, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // hash per immagazinare tutto, ma anche per parsare

        // lista che contiene tutte le room
        var roomListTemp: MutableList<Any?>

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)



        var cardRoomList= mutableListOf<Room>()
        var adapter = RoomCardListAdapter(cardRoomList)
        var rvRooms = view.findViewById<RecyclerView>(R.id.rvRooms)

        viewModelDB.getPlantsFromDBRealtime("Alive")
        viewModelDB.getPlantsFromDBRealtime("Dead")

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
           cardRoomList.clear()
            var hashTempPlantCardAlive : HashMap<String, ListPlants> =  initHashWithRoom()
            var mapTemp : HashMap<String,Any?> = HashMap()
            for((key,value) in it){
                 mapTemp  = value as HashMap<String, Any?>
                var tempPlantAlive = ManagerFirebase.fromHashMapToPlant(mapTemp)
                //posiziona le piante nella hash ognuna in una lista raggiungibile dalla chiave
                hashTempPlantCardAlive.get(tempPlantAlive.room)?.addPlantInList(tempPlantAlive)
            }

            for (room in hashTempPlantCardAlive.keys){
                if(!hashTempPlantCardAlive.getValue(room).isEmpty()){
                    cardRoomList.add(Room(room, hashTempPlantCardAlive[room]?.listPlants!!))
                }

            }

            if (viewModelDB.myPlantsAlive.value?.values?.size==null){
                cardRoomList.clear()
            }else{
                Log.d("ListaVive", viewModelDB.myPlantsAlive.value?.values?.size.toString())
                adapter = RoomCardListAdapter(cardRoomList)
                rvRooms.adapter = adapter
                rvRooms.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }



        })
        viewModelDB.returnListPlantsDied().observe(viewLifecycleOwner, Observer {

            var lisPlantsDiedTemp = mutableListOf<Plant>()
            var mapTemp : HashMap<String,Any?> = HashMap()
            for ((key,value)in it){
                mapTemp = value as HashMap<String, Any?>
                var tempPlantDied = ManagerFirebase.fromHashMapToPlant(mapTemp)
                lisPlantsDiedTemp.add(tempPlantDied)

            }
            if (!lisPlantsDiedTemp.isEmpty()){
                cardRoomList.add(Room("Plant Graveyard", lisPlantsDiedTemp,isGraveyard = true))
            }

            Log.d("ListaMorte", toString())

            if (viewModelDB.myPlantsDied.value?.values==null){

            }else{
                adapter = RoomCardListAdapter(cardRoomList)
                rvRooms.adapter = adapter
                rvRooms.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }



        })








        val noPlantsInMyPlantsTV = view.findViewById<TextView>(R.id.noPlantsInMyPlantsTV)
        if(cardRoomList.size > 0) {
            noPlantsInMyPlantsTV.visibility = View.GONE
        }
        else {
            noPlantsInMyPlantsTV.visibility = View.VISIBLE
        }



    }
    fun initHashWithRoom(): HashMap<String, ListPlants> {
        var tempHashMap : HashMap<String, ListPlants> = HashMap()
        tempHashMap["Kitchen"]=ListPlants()
        tempHashMap["Living Room"]=ListPlants()
        tempHashMap["Balcony"]=ListPlants()
        tempHashMap["Bathroom"]=ListPlants()
        tempHashMap["Dining Room"]=ListPlants()
        tempHashMap["Bedroom"]=ListPlants()


        Log.d("Stampo", tempHashMap.toString())
        return tempHashMap
    }


}


