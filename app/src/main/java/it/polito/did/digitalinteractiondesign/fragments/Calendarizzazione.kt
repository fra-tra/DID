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
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.structures.*
import java.util.ArrayList


class Calendarizzazione : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendarizzazione, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val RVparent = view.findViewById<RecyclerView>(R.id.RVparent)
        var roomList = mutableListOf<Room>()

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)

        viewModelDB.getPlantsFromDBRealtime("Alive")
        viewModelDB.getPlantsFromDBRealtime("Dead")
        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            roomList.clear()
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
                    roomList.add(Room(room, hashTempPlantCardAlive[room]?.listPlants!!))
                }

            }

            val adapter = CalendarCardListAdapter(roomList)
            RVparent.adapter = adapter
            RVparent.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

            val noPlantsInCalendarTV = view.findViewById<TextView>(R.id.noPlantsInCalendarTV)
            if(roomList.size > 0) {
                noPlantsInCalendarTV.visibility = View.GONE
            }
            else {
                noPlantsInCalendarTV.visibility = View.VISIBLE
            }


        })


    }

    fun initHashWithRoom(): HashMap<String, ListPlants> {
        var tempHashMap : HashMap<String, ListPlants> = HashMap()
        tempHashMap["Kitchen"]= ListPlants()
        tempHashMap["Living Room"]= ListPlants()
        tempHashMap["Balcony"]= ListPlants()
        tempHashMap["Bathroom"]= ListPlants()
        tempHashMap["Dining Room"]= ListPlants()
        tempHashMap["Bedroom"]= ListPlants()


        Log.d("Stampo", tempHashMap.toString())
        return tempHashMap
    }

}