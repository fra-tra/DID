package it.polito.did.digitalinteractiondesign.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.structures.Plant
import java.time.LocalDateTime
import kotlin.collections.HashMap

class CustomDeathDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.fragment_custom_death_dialog, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        var btnCancel = view.findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            this.dismiss()
        }

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)
        viewModelDB.getPlantsFromDBRealtime("Alive")

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            //salto le verifiche delle variabili

            var activePlantID = arguments?.get("activePlant")

            Log.d("IdActivePlant", activePlantID.toString())
            var tempPlant = it.get(activePlantID)
            var activePlant: Plant? =null
            if(tempPlant!=null)  activePlant= ManagerFirebase.fromHashMapToPlant(tempPlant as HashMap<String,Any?>)
            if(activePlant!=null){
                var btnRIP = view.findViewById<Button>(R.id.btnRIP)
                btnRIP.setOnClickListener {
                    // registra la morte della pianta
                    activePlant.dayDied=LocalDateTime.now().toString().split(".")[0]
                    activePlant.daysLived=ManagerFirebase.daysLived(activePlant.dayBorn.split("T")[0],activePlant.dayDied.split("T")[0]).toString().toInt()
                    activePlant.isDead=true
                    activePlant.causeDeath="SORRY FOR YOUR PLANT!"
                    var bundleActivePlant= bundleOf(Pair("activePlant",activePlant.idIdentification))
                    // amamzzo la pianta
                        ManagerFirebase.deletePlantInDB("Alive",activePlant)
                        viewModelDB.removePlant("Alive",activePlant)
                    //mi porti alla schermata con la pianta morta
                    findNavController().navigate(R.id.action_myPlantSettingsFragment_to_loadingPlantFuneralFragment,bundleActivePlant)
                }

            }

        })

    }
}