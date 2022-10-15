package it.polito.did.digitalinteractiondesign.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.databinding.FragmentLoadingAddPlantBinding
import it.polito.did.digitalinteractiondesign.databinding.FragmentLoadingPlantFuneralBinding
import it.polito.did.digitalinteractiondesign.structures.Plant

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoadingAddPlantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoadingAddPlantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var millsDuration : Long = 3000

    private var _binding : FragmentLoadingAddPlantBinding? = null
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
        _binding = FragmentLoadingAddPlantBinding.inflate(inflater, container, false)

        var w = binding.pbWaitingAdd
        w.isVisible = true

        var t = binding.textLoadingAddPlant
        t.isVisible = false
        LottieCompositionFactory.fromRawRes(context, R.raw.addplant_cropped).addListener {
            binding.addPlantAnimation.setComposition(it)
            w.isVisible = false
            t.isVisible = true
        }

        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var activePlantID = arguments?.get("activePlant")
        var a = view.findViewById<LottieAnimationView>(R.id.addPlantAnimation)
        var textPlant=view.findViewById<TextView>(R.id.plantName_loading_add)
        a.playAnimation()


        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")
        // aggiorno schermata fragment con la pianta



        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null){
                activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
                textPlant.text=activePlant.name
            }


        })



        //show dead plant after the progress bar animation is complete
        var progressBar = view.findViewById<ProgressBar>(R.id.progressBarAddPlant)
        val animator = ValueAnimator.ofInt(0, progressBar.max)
        animator.duration = millsDuration
        animator.addUpdateListener { animation ->
            progressBar.progress = (animation.animatedValue as Int)!!
        }

        //



        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //findNavController().navigate(R.id.action_loadingPlantFuneralFragment_to_myDeadPlantFragment)
                val bottomNav: BottomNavigationView = (context as Home_Activity).findViewById(R.id.bottomNavigationView)
                bottomNav.selectedItemId = R.id.piante
                var bundleActivePlant= bundleOf(Pair("activePlant",activePlantID))
                findNavController().navigate(R.id.myPlantFragment,bundleActivePlant)
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
        window?.statusBarColor = context?.let { ContextCompat.getColor(it, R.color.light_green) }!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        var window = activity?.window
        window?.statusBarColor = context?.let { ContextCompat.getColor(it, android.R.color.transparent) }!!
    }
}