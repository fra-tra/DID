package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.R
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectCalendarRangeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SelectCalendarRangeFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_select_calendar_range, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SelectCalendarRangeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SelectCalendarRangeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val defaultDates: Pair<Long, Long>? = null

        val materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker()
        materialDateBuilder.setTitleText(R.string.SelectCalendarRangeFragment_materialDateBuilder);



        //disable past days
        val now = MaterialDatePicker.todayInUtcMilliseconds()
        val constraintsBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointForward.from(now))
        materialDateBuilder.setCalendarConstraints(constraintsBuilder.build())

        materialDateBuilder.setSelection(
            androidx.core.util.Pair(MaterialDatePicker.todayInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())
        )


        val datePicker = materialDateBuilder.build()



        var radioGroup = view.findViewById<RadioGroup>(R.id.calendarRangeRadioGroup);
        var btnForever = view.findViewById<RadioButton>(R.id.btnForever)
        var btnRange = view.findViewById<RadioButton>(R.id.btnSpecificDateRange)
        var selectedRangeTv = view.findViewById<TextView>(R.id.selectedRangeTV)

        //default action
        radioGroup.check(R.id.btnForever)
        if(radioGroup.checkedRadioButtonId != R.id.btnSpecificDateRange){
            selectedRangeTv.visibility = View.INVISIBLE
        }
        else {
            selectedRangeTv.visibility = View.VISIBLE
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
            if (checkedId == R.id.btnSpecificDateRange) {
                datePicker.show(
                    requireActivity().getSupportFragmentManager(),
                    "MATERIAL_DATE_PICKER"
                );
                selectedRangeTv.visibility = View.VISIBLE


                datePicker.addOnNegativeButtonClickListener {
                    selectedRangeTv.text = "${datePicker.headerText} >"
                    Log.d("Selection at negative ", datePicker.selection.toString())
                }

                datePicker.addOnPositiveButtonClickListener {
                    selectedRangeTv.text = "${datePicker.headerText} >"
                    datePicker.selection?.let { it1 -> convertLongToTime(it1.first) }?.let { it2 ->
                        Log.d("Selection at positive ",
                            it2
                        )
                    }

                    datePicker.selection?.let { it1 -> convertLongToTime(it1.second) }?.let { it2 ->
                        Log.d("Selection at positive ",
                            it2
                        )
                    }

                //    ManagerFirebase.updateValuePlantAlive()


                }

                }

            else {
                selectedRangeTv.visibility = View.INVISIBLE
            }


        selectedRangeTv.setOnClickListener {
            datePicker.show(
                requireActivity().getSupportFragmentManager(),
                "MATERIAL_DATE_PICKER"
            );
           /* datePicker.addOnPositiveButtonClickListener {
                selectedRangeTv.text = "${datePicker.headerText} >"
            }*/
        }


        }

        var btnBack = view.findViewById<ImageButton>(R.id.backButtonSelectCalendarRange)
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
}