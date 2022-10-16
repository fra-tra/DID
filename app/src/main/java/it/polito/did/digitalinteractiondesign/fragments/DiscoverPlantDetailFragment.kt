package it.polito.did.digitalinteractiondesign.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.CollapsingToolbarLayout
import it.polito.did.digitalinteractiondesign.ManagerPlantsInfoFirestore
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant
import it.polito.did.digitalinteractiondesign.structures.PlantsInfo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoverPlantDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoverPlantDetailFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_discover_plant_detail, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoverPlantDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoverPlantDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activePlantInfoID = arguments?.get("activePlantInfo").toString()
        var listCategoryName=activePlantInfoID.split("_")

        var isliked = false

        var backBtn = view.findViewById<ImageButton>(R.id.backButtonDiscoverPlantDetail)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        var btnAddPlant = view.findViewById<Button>(R.id.btnAddPlant)
        btnAddPlant.setOnClickListener {
            var bundleActivePlant= bundleOf(Pair("activePlantInfo",activePlantInfoID))
            findNavController().navigate(R.id.action_discoverPlantDetailFragment_to_addPlantNameAndRoomFragment,bundleActivePlant)
        }

        var btnLiked = view.findViewById<ImageButton>(R.id.btnLiked)
        var nLiked = view.findViewById<TextView>(R.id.NLiked)
        btnLiked.setOnClickListener{
            if (isliked==false){
                isliked=true
                btnLiked.setImageResource(R.drawable.ic_heart_full)
                nLiked.text = ((Integer.parseInt(nLiked.text as String)) + 1).toString()
            }
            else {
                isliked=false
                btnLiked.setImageResource(R.drawable.ic_heart_empty)
                nLiked.text = ((Integer.parseInt(nLiked.text as String))- 1).toString()
            }

        }



        var plantprova=  ManagerPlantsInfoFirestore.returnPlantByCategoryByName(listCategoryName[0],listCategoryName[1])
        Log.d("Pianta creat", plantprova.toString())
        var toolbar = view.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)

        var infoTV=view.findViewById<TextView>(R.id.loremipsum)
        var consigliTV=view.findViewById<TextView>(R.id.loremipsum2)
        var difficultTV=view.findViewById<TextView>(R.id.DifficultText)
        var categoryTV=view.findViewById<TextView>(R.id.SubTitleCategory)
        var levelCommitTV=view.findViewById<TextView>(R.id.LevelCommitText)
        var imagePlant=view.findViewById<ImageView>(R.id.imagePlant)

        // da quante persone é stato aggiunto
        var nPersonTV=view.findViewById<TextView>(R.id.NPeson)




        toolbar.title=plantprova.name
        infoTV.text= Html.fromHtml(plantprova.description, Html.FROM_HTML_MODE_LEGACY)
        infoTV.setMovementMethod(LinkMovementMethod.getInstance())
        consigliTV.text=Html.fromHtml(plantprova.tips, Html.FROM_HTML_MODE_LEGACY)
        consigliTV.setMovementMethod(LinkMovementMethod.getInstance())
        difficultTV.text=plantprova.difficulty
        categoryTV.text=plantprova.category
        levelCommitTV.text=calcolaImpegno(plantprova.wateringInterval)
        Glide.with(view).load(plantprova.imageUrl).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imagePlant)

    }

    fun calcolaImpegno(valTemp:Int):String{
        var resultTemp:String =""
       if(valTemp<=2){
           resultTemp="Base"
       }else if(2<valTemp || valTemp<=4){
           resultTemp="Medium"
       }else{

        resultTemp="High"
       }
       return resultTemp
    }

}