package it.polito.did.digitalinteractiondesign.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.Group
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.CollapsingToolbarLayout
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant
import me.itangqi.waveloadingview.WaveLoadingView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WateringCanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WateringCanFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_watering_can, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WateringCanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WateringCanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")
        // aggiorno schermata fragment con la pianta

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            // ci serve sapere la pianta schiacciata

            var tempPlant = it.get("Herbs_2022-10-15T16:47:36")
            var activePlant: Plant? =null
            if(tempPlant!=null){
                activePlant=ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            }

            if(activePlant!=null){
                //gestione alert annaffiatoio vuoto
                var alertEmptyWateringCan = view.findViewById<Group>(R.id.groupAlertWateringCan)

                //GESTIONE (TEMPORANEA) PROGRESSO ANNAFFIATOIO TRAMITE SEEK BAR
                var wave = view.findViewById<WaveLoadingView>(R.id.waveLoadingView)
                wave.setAnimDuration(10000);

                var bar = view.findViewById<SeekBar>(R.id.seekBar)

               // wave.progressValue = bar.progress;
                bar.progress= activePlant.waterLevelMeasure.toInt()


                var amp = 30;

                //either isgone or isinvisible depending on the desired effect if water measure is greater than 10
                alertEmptyWateringCan.isGone = wave.progressValue >= 10

                if(wave.progressValue < amp){
                    wave.setAmplitudeRatio(wave.progressValue)
                }
                else {
                    amp = 30
                    wave.setAmplitudeRatio(amp)
                }

                bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onStopTrackingTouch(seekBar: SeekBar) {
                        // TODO Auto-generated method stub
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar) {
                        // TODO Auto-generated method stub
                    }

                    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                        // TODO Auto-generated method stub

                        wave.progressValue = progress;
                        if(progress < amp){
                            wave.setAmplitudeRatio(progress)

                        }
                        //either isgone or isinvisible depending on the desired effect if water measure is greater than 10
                        alertEmptyWateringCan.isGone = progress >= 12

                    }
                })


            }



        })
    }
}