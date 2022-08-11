package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.databinding.ActivityHomeBinding
import it.polito.did.digitalinteractiondesign.databinding.FragmentProfiloBinding
import it.polito.did.digitalinteractiondesign.structures.Plant
import it.polito.did.digitalinteractiondesign.structures.ProfileLikedPlantAdapter


class Profilo : Fragment() {
    private lateinit var binding: FragmentProfiloBinding
  // variables for data that come back from firebase

    private lateinit var email : TextView
    private lateinit var recyclerViewPlants: RecyclerView
  //------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfiloBinding.inflate(layoutInflater)
        (activity as Home_Activity).getDataOfUser(binding.tvEmail)


        // Inflate the layout for this fragment
       // (activity as Home_Activity).getDataOfUser(email)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btnProfileSettings= view.findViewById<ImageView>(R.id.imageSettings)
        btnProfileSettings.setOnClickListener{
            findNavController().navigate(R.id.action_profilo_to_profiloSettings)
        }

        var editSkillLevel = view.findViewById<TextView>(R.id.grid_textView6)
        editSkillLevel.setOnClickListener {
            findNavController().navigate(R.id.action_profilo_to_editSkillLevelFragment)
        }

        var editCommitmentLevel = view.findViewById<TextView>(R.id.grid_textView8)
        editCommitmentLevel.setOnClickListener {
            findNavController().navigate(R.id.action_profilo_to_editCommitmentLevelFragment)
        }

        //adapter View Plants liked
        var plantList = mutableListOf(
            Plant("Basilico", null),
            Plant("Origano", null),
            Plant("Pothos", null),
            Plant("Cactus", null),
            Plant("Rosmarino", null),
        )

        val adapterPlantsLiked= ProfileLikedPlantAdapter(plantList)
        recyclerViewPlants=view.findViewById(R.id.RV_plantsLiked)

        recyclerViewPlants.adapter=adapterPlantsLiked


        val layoutManagerCategories = GridLayoutManager(activity, 3)
        recyclerViewPlants.layoutManager = layoutManagerCategories
        (recyclerViewPlants.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }

}