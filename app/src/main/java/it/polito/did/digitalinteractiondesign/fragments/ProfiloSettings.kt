package it.polito.did.digitalinteractiondesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.activity.Home_Activity


class ProfiloSettings : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth= FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilo_settings, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var btnLogOut= view.findViewById<Button>(R.id.logOutButton)
        btnLogOut.setOnClickListener{
            //call the method to signOut of the application
            (activity as Home_Activity).signOut()

        }
    }
}