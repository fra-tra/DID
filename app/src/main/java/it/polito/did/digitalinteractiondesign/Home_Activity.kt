package it.polito.did.digitalinteractiondesign

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.digitalinteractiondesign.databinding.ActivityHomeBinding
import androidx.navigation.ui.NavigationUI


import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController


class Home_Activity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityHomeBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""

    //----------NAVIGATION MENU---------------------
    lateinit var navigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // configure Action Bar
        actionBar=supportActionBar!!
        actionBar.title="Profile"

        //init firebase auth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //current view like bottom menu
        val bottomNavigationView =findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController: NavController = Navigation.findNavController(this,R.id.fragment)
        setupWithNavController(bottomNavigationView, navController);
        // cambio del nome della bara di navigazione in base alla schermata in cui ci si trova
        //val appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.discover, R.id.piante, R.id.calendarizzazione,R.id.profilo))
        //setupWithNavController(bottomNavigationView, navController)

       // Log.i("ciao","$navController")
        //bottomNavigationView.setupWithNavController(navController)







    //handle click, logout
       // binding.logOutBtn.setOnClickListener {
        //    firebaseAuth.signOut()
        //    checkUser()
        //}




}

    private fun checkUser() {
        //check user is logged or not
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            //user not null, user is logged in, get user info
            val email= firebaseUser.email
            // set to text view
           // binding.emailTv.text=email
        }else {
            //user is null, user is not logged
            startActivity(Intent(this,Login_Activity::class.java))
            finish()
        }
    }
}
