package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.R

// TODO: Rename parameter arguments, choose names that match

class EditSkillLevelFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_skill_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var backBtn = view.findViewById<ImageButton>(R.id.backButtonEditSkillLevel)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        var skill01 = view.findViewById<CardView>(R.id.SL_Level01)
        skill01.setOnClickListener {
            findNavController().navigateUp()
        }

        var skill02 = view.findViewById<CardView>(R.id.SL_Level02)
        skill02.setOnClickListener {
            findNavController().navigateUp()
        }

        var skill03 = view.findViewById<CardView>(R.id.SL_Level03)
        skill03.setOnClickListener {
            findNavController().navigateUp()
        }

        var skill04 = view.findViewById<CardView>(R.id.SL_Level04)
        skill04.setOnClickListener {
            findNavController().navigateUp()
        }

        var skill05 = view.findViewById<CardView>(R.id.SL_Level05)
        skill05.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}