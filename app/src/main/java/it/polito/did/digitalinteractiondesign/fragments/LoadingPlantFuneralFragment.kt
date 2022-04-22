package it.polito.did.digitalinteractiondesign.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.FragmentLoadingPlantFuneralBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var millsDuration : Long = 3000

/**
 * A simple [Fragment] subclass.
 * Use the [LoadingPlantFuneralFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoadingPlantFuneralFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentLoadingPlantFuneralBinding? = null
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
        // Inflate the layout for this fragment
        _binding = FragmentLoadingPlantFuneralBinding.inflate(inflater, container, false)

        var w = binding.pbWaitingFuneral
        w.isVisible = true

        var t = binding.textLoadingFuneralPlant
        t.isVisible = false
        LottieCompositionFactory.fromRawRes(context, R.raw.funeral_cropped).addListener {
            binding.funeralPlantAnimation.setComposition(it)
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
         * @return A new instance of fragment LoadingPlantFuneralFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoadingPlantFuneralFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var a = view.findViewById<LottieAnimationView>(R.id.funeralPlantAnimation)
        a.playAnimation()

        //show dead plant after the progress bar animation is complete
        var progressBar = view.findViewById<ProgressBar>(R.id.progressBarFuneralPlant)
        val animator = ValueAnimator.ofInt(0, progressBar.max)
        animator.duration = millsDuration
        animator.addUpdateListener { animation ->
            progressBar.progress = (animation.animatedValue as Int)!!
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                findNavController().navigate(R.id.action_loadingPlantFuneralFragment_to_myDeadPlantFragment)
            }
        })
        animator.start()

        //disable back button when loading
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })


        //configure activity status bar color
        var window = activity?.window
        window?.statusBarColor = context?.let { ContextCompat.getColor(it, R.color.light_purple) }!!


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        var window = activity?.window
        window?.statusBarColor = context?.let { ContextCompat.getColor(it, android.R.color.transparent) }!!
    }


}