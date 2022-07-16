package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.structures.Calendar
import it.polito.did.digitalinteractiondesign.structures.CalendarAdapter
import it.polito.did.digitalinteractiondesign.structures.CalendarChild
import java.util.ArrayList


class Calendarizzazione : Fragment() {

    var calendarAdapter: CalendarAdapter? = null
    var calendarArrayList: ArrayList<Calendar>? = null
    var calendarChildArrayList: ArrayList<CalendarChild>? = null
    var RVparent: RecyclerView? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendarizzazione, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RVparent = view.findViewById<RecyclerView>(R.id.RVparent)

       // val orderId : Array<String> = arrayOf()
       val orderId = arrayOf(
            "Cucina",
            "Salotto",
            "Bagno",
            "Balcone",
            "Camera da Letto 1",
            "Camera da Letto 2"
        )
        val itemName = arrayOf("Basilico", "Olmo", "Cactus")
        val switchStatus = arrayOf(false, true, false)
        calendarArrayList = ArrayList()
        calendarChildArrayList = ArrayList()

        for (i in orderId.indices) {
            val calendar = Calendar(orderId[i])
            calendarArrayList!!.add(calendar)
            if (i < itemName.size) {
                val calendarChild = CalendarChild(itemName[i], switchStatus[i])
                calendarChildArrayList!!.add(calendarChild)
            }
        }
        val tmpActivity : Home_Activity = activity as Home_Activity
        calendarAdapter = CalendarAdapter(tmpActivity, calendarArrayList, calendarChildArrayList)
        val linearLayoutManager = LinearLayoutManager(tmpActivity)
        RVparent!!.layoutManager = linearLayoutManager
        RVparent!!.adapter = calendarAdapter


        val noPlantsInCalendarTV = view.findViewById<TextView>(R.id.noPlantsInCalendarTV)
        if(orderId.size > 0) {
            noPlantsInCalendarTV.visibility = View.GONE
        }
        else {
            noPlantsInCalendarTV.visibility = View.VISIBLE
        }
    }

}