package it.polito.did.digitalinteractiondesign.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.FragmentLoadingPlantFuneralBinding
import it.polito.did.digitalinteractiondesign.databinding.FragmentLoadingWaterPlantBinding
import it.polito.did.digitalinteractiondesign.structures.Plant

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var millsDuration : Long = 10000

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
        var activePlantID = arguments?.get("activePlant")
        var a = view.findViewById<LottieAnimationView>(R.id.waterPlantAnimation)
        var textPlant=view.findViewById<TextView>(R.id.plantName_loading_water)
        a.playAnimation()

        //show my plant after the progress bar animation is complete
        //TO BE UPDATED: now the screen changes after three seconds
        //the screen has to change once the plant has reached the correct amount of humidity
        var progressBar = view.findViewById<ProgressBar>(R.id.progressBarWaterPlant)
        val animator = ValueAnimator.ofInt(0, progressBar.max)
        animator.duration = millsDuration
        animator.addUpdateListener { animation ->
            progressBar.progress = (animation.animatedValue as Int)!!
        }

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                findNavController().navigateUp()
            }
        })
        animator.start()
        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")
        // aggiorno schermata fragment con la pianta

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null){
                activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            }

            if(activePlant!=null){
                Log.d("BOBBE",activePlant.name )
                textPlant.text=activePlant.name

            }

        })

        //disable back button when loading
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

        //disable back button when loading
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })

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
