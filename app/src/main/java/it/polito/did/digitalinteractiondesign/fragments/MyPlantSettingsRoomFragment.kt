package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Room
import it.polito.did.digitalinteractiondesign.structures.RoomImageAdapter

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
        val btnBack = view.findViewById<ImageButton>(R.id.backButtonRoomSettings)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        //TEST ROOM LIST
        val rooms = arrayListOf(
            Room("Kitchen"), Room ("Living Room"), Room("Balcony"), Room("Kitchen"), Room ("Living Room"), Room("Balcony")

        )

        val adapter = RoomImageAdapter(rooms)
        val rv = view.findViewById<RecyclerView>(R.id.rvRoomImages)
        rv.adapter = adapter
       // rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
       // val layoutManager = GridLayoutManager(activity, 2)
        val layoutManager = GridLayoutManager(activity, 2)
        rv.layoutManager = layoutManager
        (rv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        var btnAddRoom = view.findViewById<ImageView>(R.id.btnAddRoom)
        btnAddRoom.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantSettingsRoomFragment_to_addRoomFragment)
        }

    }
}