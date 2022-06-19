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
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditCommitmentLevelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditCommitmentLevelFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_commitment_level, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var backBtn = view.findViewById<ImageButton>(R.id.backButtonEditCommitmentLevel)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        var level01 = view.findViewById<CardView>(R.id.CL_Level01)
        level01.setOnClickListener {
            findNavController().navigateUp()
        }

        var level02 = view.findViewById<CardView>(R.id.CL_Level02)
        level02.setOnClickListener {
            findNavController().navigateUp()
        }

        var level03 = view.findViewById<CardView>(R.id.CL_Level03)
        level03.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}