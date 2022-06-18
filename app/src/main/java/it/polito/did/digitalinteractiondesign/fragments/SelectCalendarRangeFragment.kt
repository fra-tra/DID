package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import it.polito.did.digitalinteractiondesign.R
import kotlinx.coroutines.selects.select
import org.w3c.dom.Text


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

        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.dateRangePicker()
        materialDateBuilder.setTitleText("SELECT A DATE");

        //disable past days
        val now = MaterialDatePicker.todayInUtcMilliseconds()
        val constraintsBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointForward.from(now))
        materialDateBuilder.setCalendarConstraints(constraintsBuilder.build())


        val datePicker = materialDateBuilder.build()

        var radioGroup = view.findViewById<RadioGroup>(R.id.calendarRangeRadioGroup);
        var btnForever = view.findViewById<RadioButton>(R.id.btnForever)
        var btnRange = view.findViewById<RadioButton>(R.id.btnSpecificDateRange)
        var selectedRangeTv = view.findViewById<TextView>(R.id.selectedRangeTV)

        //default action
        radioGroup.check(R.id.btnForever)
        if(radioGroup.checkedRadioButtonId == R.id.btnForever){
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
                datePicker.addOnPositiveButtonClickListener {
                    selectedRangeTv.visibility = View.VISIBLE
                    selectedRangeTv.text = "${datePicker.headerText} + > "
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
            datePicker.addOnPositiveButtonClickListener {
                selectedRangeTv.text = "${datePicker.headerText} + > "
            }
        }

            var btnBack = view.findViewById<ImageButton>(R.id.backButtonSelectCalendarRange)
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}