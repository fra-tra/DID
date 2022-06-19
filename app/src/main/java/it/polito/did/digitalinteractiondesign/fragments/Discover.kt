package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.CategoryPlantAdapter
import it.polito.did.digitalinteractiondesign.structures.LikedAndPopularPlantsAdapter
import it.polito.did.digitalinteractiondesign.structures.Plant
import it.polito.did.digitalinteractiondesign.structures.PlantHomeSummaryAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Discover.newInstance] factory method to
 * create an instance of this fragment.
 */
class Discover : Fragment() {
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
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Discover.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Discover().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val plantList = mutableListOf(
            Plant("Basilico", null, false, 16.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Origano", null, false, 25.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Pothos", null, false, 42.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Cactus", null, false, 8.0,  arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Rosmarino", null, false, 62.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
        )

        val categoriesList = mutableListOf("Succulentae e Cactus", "Piante Aromatiche", "Cactus", "Boh un'altra")

        val adapterLiked = LikedAndPopularPlantsAdapter(plantList)
        val likedPlantsRV = view.findViewById<RecyclerView>(R.id.likedPlantsRV)
        likedPlantsRV.adapter = adapterLiked
        likedPlantsRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val adapterPopular = LikedAndPopularPlantsAdapter(plantList)
        val popularPlantsRV = view.findViewById<RecyclerView>(R.id.popularPlantsRV)
        popularPlantsRV.adapter = adapterPopular
        popularPlantsRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val adapterCategories = CategoryPlantAdapter(categoriesList)
        val categoriesRV = view.findViewById<RecyclerView>(R.id.categoriesRV)
        categoriesRV.adapter = adapterCategories
       // categoriesRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val layoutManagerCategories = GridLayoutManager(activity, 2)
        categoriesRV.layoutManager = layoutManagerCategories
        (categoriesRV.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }
}