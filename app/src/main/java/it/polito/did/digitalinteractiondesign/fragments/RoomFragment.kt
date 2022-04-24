package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        //TEST LIST
        var plantList = mutableListOf(
            Plant("Basilico", null, false),
            Plant("Origano", null, false),
            Plant("Pothos", null, false),
            Plant("Cactus", null, false),
            Plant("Rosmarino", null, false),
        )

        val adapter = PlantCardListAdapter(plantList)
        val rvPlants = view.findViewById<RecyclerView>(R.id.rvRoomPlants)
        rvPlants.adapter = adapter
        rvPlants.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        var backBtn = view.findViewById<Button>(R.id.backButtonRoom)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}