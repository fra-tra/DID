package it.polito.did.digitalinteractiondesign.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import it.polito.did.digitalinteractiondesign.R

class CustomDeathDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as dialog or embedded fragment
        return inflater.inflate(R.layout.fragment_custom_death_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var btnRIP = view.findViewById<Button>(R.id.btnRIP)
        btnRIP.setOnClickListener {
            findNavController().navigate(R.id.action_myPlantSettingsFragment_to_loadingPlantFuneralFragment)
        }

        var btnCancel = view.findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            this.dismiss()
        }

    }
}