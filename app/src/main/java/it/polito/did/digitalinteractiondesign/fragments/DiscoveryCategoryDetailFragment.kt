package it.polito.did.digitalinteractiondesign.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.contains
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.*
import kotlin.collections.contains as contains1


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiscoveryCategoryDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiscoveryCategoryDetailFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_discovery_category_detail, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiscoveryCategoryDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiscoveryCategoryDetailFragment().apply {
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
            Plant("Basilico", null, false, 16.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Origano", null, false, 25.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Pothos", null, false, 42.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Cactus", null, false, 8.0,  arrayOf(12.0, 18.0, 60.0, 66.0)),
            Plant("Rosmarino", null, false, 62.0, arrayOf(12.0, 18.0, 60.0, 66.0)),
        )
        plantList.sortBy { it.name }

        val adapter = CategoryPlantDetailAdapter(plantList)
        val plantsInCategoryRV = view.findViewById<RecyclerView>(R.id.plantsInCategoryRV)
        plantsInCategoryRV.adapter = adapter
        plantsInCategoryRV.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        var backBtn = view.findViewById<ImageButton>(R.id.backButtonDiscoverCategoryDetail)
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


        val search = view.findViewById<SearchView>(R.id.searchviewcategory)
        val cancel = view.findViewById<TextView>(R.id.cancelSearchView)
        cancel.visibility = View.GONE

        cancel.setOnClickListener {
            cancel.visibility = View.GONE
            search.onActionViewCollapsed()
        }
        search.setOnQueryTextFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                cancel.visibility = View.VISIBLE
            }

            /* else {
                    nested.visibility = View.VISIBLE
                }*/
        }

        search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        }
        )

    }
}


