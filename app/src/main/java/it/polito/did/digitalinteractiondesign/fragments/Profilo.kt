package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import it.polito.did.digitalinteractiondesign.*
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.databinding.FragmentProfiloBinding
import it.polito.did.digitalinteractiondesign.structures.*


class Profilo : Fragment() {
    private lateinit var binding: FragmentProfiloBinding
  // variables for data that come back from firebase

    private lateinit var email : TextView
    private lateinit var recyclerViewPlants: RecyclerView
    var userTemp :User =User("Missing info!","Missing info!","Missing info!","Missing info!")
  //------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfiloBinding.inflate(layoutInflater)






        // Inflate the layout for this fragment
       // (activity as Home_Activity).getDataOfUser(email)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)

        viewModelDB.getUserInfo(ManagerFirebase.refereceDBUser.toString())

        viewModelDB.returndListUserData().observe(viewLifecycleOwner, Observer {

            var tvEmail=view.findViewById<TextView>(R.id.tvEmail)
            var tvName=view.findViewById<TextView>(R.id.tvName)
            var tvCity=view.findViewById<TextView>(R.id.grid_textView4)
            var tvAbility=view.findViewById<TextView>(R.id.grid_textView6)
            var tvDedication=view.findViewById<TextView>(R.id.grid_textView8)

            tvEmail.text=it.get("Email").toString()
            tvName.text=it.get("Name").toString()
            tvCity.text=it.get("City").toString() + " >"
            var bundleCityNow=it.get("City").toString()

            tvAbility.text= it.get("Ability Level").toString()
            tvDedication.text= it.get("Dedication Level").toString()



            var editSkillLevel = view.findViewById<TextView>(R.id.grid_textView6)
            editSkillLevel.setOnClickListener {

                findNavController().navigate(R.id.action_profilo_to_editSkillLevelFragment)
            }

            var editCommitmentLevel = view.findViewById<TextView>(R.id.grid_textView8)
            editCommitmentLevel.setOnClickListener {
                findNavController().navigate(R.id.action_profilo_to_editCommitmentLevelFragment)
            }

            tvCity.setOnClickListener {
                var bundleActivePlant= bundleOf(Pair("cityNow",bundleCityNow))
                findNavController().navigate(R.id.action_profilo_to_profileSetCityFragment,bundleActivePlant)
            }

        })





        var btnProfileSettings= view.findViewById<ImageView>(R.id.imageSettings)
        btnProfileSettings.setOnClickListener{
            findNavController().navigate(R.id.action_profilo_to_profiloSettings)
        }



        var plantList = mutableListOf<PlantsInfo>()
        for((key,value) in ManagerPlantsInfoFirestore.hasMapPlantByCategory){
            var tempPlantCategory: PlantCategory = PlantCategory("Standard", ListPlantsInfo())
            tempPlantCategory.name=key
            tempPlantCategory.plants= value
            // Log.d("N Plants in", key.toString()+ tempPlantCategory.plants.listPlants.size)
            for(plant in tempPlantCategory.plants.listPlants){
                if(!plant.isEmpty()){
                    plantList.add(ManagerPlantsInfoFirestore.fromHashMapToPlantInfo(plant))
                }
            }


        }

        //adapter View Plants liked


        val adapterPlantsLiked= ProfileLikedPlantAdapter(plantList.takeLast(4))
        recyclerViewPlants=view.findViewById(R.id.RV_plantsLiked)

        recyclerViewPlants.adapter=adapterPlantsLiked


        val layoutManagerCategories = GridLayoutManager(activity, 3)
        recyclerViewPlants.layoutManager = layoutManagerCategories
        (recyclerViewPlants.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }

}