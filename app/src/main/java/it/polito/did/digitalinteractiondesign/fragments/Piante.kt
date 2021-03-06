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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Piante.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Piante().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TEST DATA
        var roomList = mutableListOf(
            Room("Kitchen",
                mutableListOf( Plant("Sanseveria", null, false),
                    Plant("Basilico", null, false),
                    Plant("Rosmarino", null, false),
                    Plant("Cactus", null, false),
                    Plant("Origano", null, false)
                )),
            Room("Plant Graveyard",
                mutableListOf( Plant("Basilico", null, true),
                    Plant("Rosmarino", null, true),
                    Plant("Origano", null, true)
                ))

        )

      //  val roomList : MutableList<Room> = mutableListOf()

        val adapter = RoomCardListAdapter(roomList)
        val rvRooms = view.findViewById<RecyclerView>(R.id.rvRooms)
        rvRooms.adapter = adapter
        rvRooms.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val noPlantsInMyPlantsTV = view.findViewById<TextView>(R.id.noPlantsInMyPlantsTV)
        if(roomList.size > 0) {
            noPlantsInMyPlantsTV.visibility = View.GONE
        }
        else {
            noPlantsInMyPlantsTV.visibility = View.VISIBLE
        }

    }


}