package it.polito.did.digitalinteractiondesign.fragments

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.FragmentLoadingPlantFuneralBinding
import it.polito.did.digitalinteractiondesign.databinding.FragmentLoadingWaterPlantBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoadingWaterPlantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoadingWaterPlantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentLoadingWaterPlantBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

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
        _binding = FragmentLoadingWaterPlantBinding.inflate(inflater, container, false)

        var w = binding.pbWaitingWater
        w.isVisible = true

        var t = binding.textLoadingWaterPlant
        t.isVisible = false
        LottieCompositionFactory.fromRawRes(context, R.raw.watering_cropped).addListener {
            binding.waterPlantAnimation.setComposition(it)
            w.isVisible = false
            t.isVisible = true
        }

        val view = binding.root
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoadingWaterPlantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoadingWaterPlantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var a = view.findViewById<LottieAnimationView>(R.id.waterPlantAnimation)
        a.playAnimation()

        //configure activity status bar color
        var window = activity?.window
        window?.statusBarColor = context?.let { ContextCompat.getColor(it, R.color.light_blue) }!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        var window = activity?.window
        window?.statusBarColor = context?.let { ContextCompat.getColor(it, android.R.color.transparent) }!!
    }
}
