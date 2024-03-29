package it.polito.did.digitalinteractiondesign.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.FragmentLoadingPlantFuneralBinding
import it.polito.did.digitalinteractiondesign.structures.Plant


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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activePlantID = arguments?.get("activePlant")
        var a = view.findViewById<LottieAnimationView>(R.id.funeralPlantAnimation)
        var textPlant=view.findViewById<TextView>(R.id.plantName_loading_funeral)
        a.playAnimation()



        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Dead")

        viewModelDB.returnListPlantsDied().observe(viewLifecycleOwner, Observer {
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null

            if(tempPlant!=null){

                activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
                textPlant.text=activePlant.name
            }


        })


        //var plantTemp=viewModelDB.getPlantByID(activePlantID.toString())
        //Log.d("TestNabo", plantTemp.toString())
        // aggiorno schermata fragment con la pianta

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
                var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
                findNavController().navigate(R.id.action_loadingPlantFuneralFragment_to_myDeadPlantFragment,bundleActivePlant)
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