package it.polito.did.digitalinteractiondesign.fragments

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.structures.Plant
import it.polito.did.digitalinteractiondesign.structures.PlantHomeSummaryAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var minTemperatureDanger = 10
    var minTemperatureWarning = 15

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
       // (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var plantList = mutableListOf(
            Plant("Basilico", null, false, 16.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Origano", null, false, 25.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Pothos", null, false, 42.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Cactus", null, false, 8.0,  arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Rosmarino", null, false, 62.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
        )

      //  val plantList : MutableList<Plant> = mutableListOf()

        val adapter = PlantHomeSummaryAdapter(plantList)
        val rvPlantsHome = view.findViewById<RecyclerView>(R.id.rvPlantsHome)
        rvPlantsHome.adapter = adapter
        rvPlantsHome.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val noPlantsInHomeGroup = view.findViewById<Group>(R.id.noPlantsInHomeGroup)
        val fabAddPlantFromHome = view.findViewById<FloatingActionButton>(R.id.btnAddPlantFromHome)
        val noPlantsInHomeTV = view.findViewById<TextView>(R.id.noPlantsInHomeTV)

        //mostra testo + fab se non ho registrato piante
        //DA CAMBIARE ASCOLTANDO IL VIEW MODEL PERCH?? ORA GESTITO CON LA LISTA LOCALE DI PIANTE SOLO ALL'INIZIO
        if(plantList.size > 0) {
            fabAddPlantFromHome.visibility = View.GONE
            noPlantsInHomeTV.visibility = View.GONE
        }

        else {
            fabAddPlantFromHome.visibility = View.VISIBLE
            noPlantsInHomeTV.visibility = View.VISIBLE
            fabAddPlantFromHome.setOnClickListener{
                val bottomNav: BottomNavigationView = (context as Home_Activity).findViewById(R.id.bottomNavigationView)
                bottomNav.selectedItemId = R.id.discover
                // findNavController().navigate(R.id.discover)
            }
        }


    }

    //general function to check if either temperature, humidity or brightness measures need to be alerted with a danger or warning icon
   /* private fun showMeasureAlert(progressBar: ProgressBar, imageToShow: ImageView, minMeasureDanger: Int, maxMeasureDanger: Int, minMeasureWarning: Int, maxMeasureWarning: Int) {
        if(progressBar.progress <= minMeasureDanger) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_danger)
        }
        else if (progressBar.progress <= minMeasureWarning) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_warning)
        }
        else if (progressBar.progress >= maxMeasureDanger) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_danger)
        }
        else if (progressBar.progress >= maxMeasureWarning) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_warning)
        }
        else {
            imageToShow.isVisible = false
        }


    }*/
}