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

        var roomList = mutableListOf(

            Room("Kitchen",
                mutableListOf( Plant("Sanseveria", null),
                    Plant("Basilico", null),
                    Plant("Rosmarino", null),
                    Plant("Cactus", null),
                    Plant("Origano", null)
                )),
            Room("Bathroom",
                mutableListOf( Plant("Basilico", null),
                    Plant("Rosmarino", null),
                    Plant("Origano", null)
                ))

        )
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
    }

}