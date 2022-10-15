package it.polito.did.digitalinteractiondesign.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.CollapsingToolbarLayout
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyDeadPlantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyDeadPlantFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_my_dead_plant, container, false)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyDeadPlantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyDeadPlantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var toolbarplant = view.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarMyDeadPlant)
        //toolbar.title=plantprova.categoryPlant // categoria pianta
        var infoDeadPlantTV= view.findViewById<TextView>(R.id.infoDeadPlant)
        var adviceDeadPlantTV= view.findViewById<TextView>(R.id.adviceDeadPlant)


        var backBtn = view.findViewById<ImageButton>(R.id.backButtonMyDeadPlant)

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Dead")
        // aggiorno schermata fragment con la pianta

        viewModelDB.returnListPlantsDied().observe(viewLifecycleOwner, Observer {
            var deadPlantNameTV=view.findViewById<TextView>(R.id.deadPlantNameTV)
            var datesDeadPlantTV=view.findViewById<TextView>(R.id.datesDeadPlantTV)
            var causeDeathTextTV=view.findViewById<TextView>(R.id.causeDeathTextTV)
            var dayLivedTV=view.findViewById<TextView>(R.id.daysLivedTV)
            var wateredTimesTV=view.findViewById<TextView>(R.id.wateredTimesTV)
            var subTitleCategoryTV=view.findViewById<TextView>(R.id.SubTitleCategory)
            var imagePlant=view.findViewById<ImageView>(R.id.imagePlant)

            var activePlantID = arguments?.get("activePlant")

            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null){
                activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            }
            if(activePlant!=null){
                toolbarplant.title=activePlant.name
                deadPlantNameTV.text=activePlant.name
                infoDeadPlantTV.text= Html.fromHtml(activePlant.information, Html.FROM_HTML_MODE_LEGACY)
                infoDeadPlantTV.setMovementMethod(LinkMovementMethod.getInstance())
                adviceDeadPlantTV.text=Html.fromHtml(activePlant.advice, Html.FROM_HTML_MODE_LEGACY)
                adviceDeadPlantTV.setMovementMethod(LinkMovementMethod.getInstance())
                subTitleCategoryTV.text=activePlant.categoryPlant

                causeDeathTextTV.text=activePlant.causeDeath + String(Character.toChars(0x1F614))
                datesDeadPlantTV.text="${activePlant.dayBorn.split("T")[0]}"+ " -> " + "${activePlant.dayDied.split("T")[0]}"
                wateredTimesTV.text=activePlant.timesWetted.toString()
                Glide.with(view).load(activePlant.image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imagePlant)
                dayLivedTV.text= activePlant.daysLived.toString()

            }

            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        })







    }



}