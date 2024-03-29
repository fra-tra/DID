package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import it.polito.did.digitalinteractiondesign.ListPlantsInfo
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.*
import it.polito.did.digitalinteractiondesign.ManagerPlantsInfoFirestore


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
        var plantListLiked = mutableListOf<PlantsInfo>()
        var plantListPopular = mutableListOf<PlantsInfo>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        //val categoriesList : MutableList<HashMap<String,Any>> = mutableListOf(hashMapOf())
        var tempListCategory = mutableListOf<PlantCategory>()
        val plantSearchList = mutableListOf<PlantsInfo>()
        for((key,value) in ManagerPlantsInfoFirestore.hasMapPlantByCategory){
            var tempPlantCategory:PlantCategory= PlantCategory("Standard", ListPlantsInfo())
            tempPlantCategory.name=key
            tempPlantCategory.plants= value
            tempListCategory.add(tempPlantCategory)
           // Log.d("N Plants in", key.toString()+ tempPlantCategory.plants.listPlants.size)
            for(plant in tempPlantCategory.plants.listPlants){
               if(!plant.isEmpty()){
                   plantSearchList.add(ManagerPlantsInfoFirestore.fromHashMapToPlantInfo(plant))
               }
            }


        }
        plantListLiked= plantSearchList.takeLast(4) as MutableList<PlantsInfo>
        plantListPopular= plantSearchList.take(3) as MutableList<PlantsInfo>


        plantSearchList.sortBy { it.name}

        val adapterLiked = LikedAndPopularPlantsAdapter(plantListLiked)
        val likedPlantsRV = view.findViewById<RecyclerView>(R.id.likedPlantsRV)
        likedPlantsRV.adapter = adapterLiked
        likedPlantsRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val adapterPopular = LikedAndPopularPlantsAdapter(plantListPopular)
        val popularPlantsRV = view.findViewById<RecyclerView>(R.id.popularPlantsRV)
        popularPlantsRV.adapter = adapterPopular
        popularPlantsRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val adapterCategories = CategoryPlantAdapter(tempListCategory)
        val categoriesRV = view.findViewById<RecyclerView>(R.id.categoriesRV)
        categoriesRV.adapter = adapterCategories
        categoriesRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val layoutManagerCategories = GridLayoutManager(activity, 2)
        categoriesRV.layoutManager = layoutManagerCategories
        (categoriesRV.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false



        val adapterFilter = CategoryPlantDetailAdapter(plantSearchList)
        val plantsFilterDiscoverRV = view.findViewById<RecyclerView>(R.id.plantsFilterDiscoverRV)
        plantsFilterDiscoverRV.adapter = adapterFilter
        plantsFilterDiscoverRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)




        val search = view.findViewById<androidx.appcompat.widget.SearchView>(R.id.searchViewDiscover)
        val nested = view.findViewById<NestedScrollView>(R.id.nestedDiscover)
        val cancel = view.findViewById<TextView>(R.id.cancelSearchView)
        cancel.visibility = View.GONE

     /*  search.setOnClickListener {
           cancel.visibility = View.VISIBLE
           nested.visibility = View.INVISIBLE
       plantsFilterDiscoverRV.visibility = View.VISIBLE} */

        cancel.setOnClickListener { nested.visibility = View.VISIBLE
        cancel.visibility = View.GONE
            plantsFilterDiscoverRV.visibility = View.INVISIBLE
            search.onActionViewCollapsed()
        }
        search.setOnQueryTextFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                Log.d("SEARCH DEVBUG", "CLICK")
                cancel.visibility = View.VISIBLE
                nested.visibility = View.INVISIBLE
                plantsFilterDiscoverRV.visibility = View.VISIBLE
            }

        /* else {
                nested.visibility = View.VISIBLE
            }*/
        }
        search?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterFilter.filter.filter(newText)
               // Log.d("AdapterFilter", adapterFilter.filter.filter(newText).toString())
                return false
            }
        }
        )

    }
}
