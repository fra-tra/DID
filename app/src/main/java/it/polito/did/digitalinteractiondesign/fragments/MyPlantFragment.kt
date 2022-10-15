package it.polito.did.digitalinteractiondesign.fragments

import android.media.Image
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
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
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlantFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_my_plant, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPlantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPlantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activePlantID = arguments?.get("activePlant")
        MyPlantWaterAndCalendarCTAFragment.activePlantID=activePlantID.toString()
        MyPlantStatsFragment.activePlantID=activePlantID.toString()


        var backBtn = view.findViewById<ImageButton>(R.id.backButtonMyPlant)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")
        // aggiorno schermata fragment con la pianta

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            // ci serve sapere la pianta schiacciata

            var toolbar = view.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarMyPlant)
            var btnSettings = view.findViewById<ImageButton>(R.id.settings)
            var infoTV=view.findViewById<TextView>(R.id.loremipsum)
            var tipsTV=view.findViewById<TextView>(R.id.loremipsum2)
            var categoryTV=view.findViewById<TextView>(R.id.SubTitleCategory)
            var imagePlant=view.findViewById<ImageView>(R.id.imagePlant)



            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null){
               activePlant=ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            }

            if(activePlant!=null){
                toolbar.title=activePlant.name
                infoTV.text= Html.fromHtml(activePlant.information, Html.FROM_HTML_MODE_LEGACY)
                infoTV.setMovementMethod(LinkMovementMethod.getInstance())
                tipsTV.text=Html.fromHtml(activePlant.advice, Html.FROM_HTML_MODE_LEGACY)
                tipsTV.setMovementMethod(LinkMovementMethod.getInstance())
                categoryTV.text=activePlant.categoryPlant
                Glide.with(view).load(activePlant.image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imagePlant)

            }

            // scheramata settings della pianta identificata
            var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
            btnSettings.setOnClickListener {
                findNavController().navigate(R.id.action_myPlantFragment_to_myPlantSettingsFragment,bundleActivePlant)
            }

        })






    }
}