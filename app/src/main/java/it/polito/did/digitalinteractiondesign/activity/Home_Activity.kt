package it.polito.did.digitalinteractiondesign.activity


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ActivityHomeBinding


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
        actionBar.title="Piant-e"
        actionBar.hide()

        //init firebase auth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //current view like bottom menu
        val bottomNavigationView =findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController: NavController = Navigation.findNavController(this, R.id.fragment)
        setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnItemSelectedListener { item ->

            // gestione eccezioni da implementare
            // eliminare elementi del back stack

            if (item.itemId != R.id.piante) {
                navController.popBackStack(R.id.myPlantFragment, false)
            }
            if(item.itemId!=R.id.profilo){
                navController.popBackStack(R.id.profilo,false)
            }

            if(item.itemId!=R.id.discover){
                navController.popBackStack(R.id.discoverPlantDetailFragment,false)
            }


            // In order to get the expected behavior, you have to call default Navigation method manually
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }


        bottomNavigationView.setOnItemReselectedListener {
                item ->
            // Pop everything up to the reselected item
            val reselectedDestinationId = item.itemId
            navController.popBackStack(reselectedDestinationId, false)
        }

        //Hide bottom navigation bar when showing loading screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loadingPlantFuneralFragment ||
                destination.id == R.id.loadingWaterPlantFragment ||
                destination.id == R.id.loadingAddPlantFragment) {

                bottomNavigationView.visibility = View.INVISIBLE
            } else {

                bottomNavigationView.visibility = View.VISIBLE
            }
        }

        // cambio del nome della bara di navigazione in base alla schermata in cui ci si trova
        //val appBarConfiguration = AppBarConfiguration(setOf(R.id.home, R.id.discover, R.id.piante, R.id.calendarizzazione,R.id.profilo))
        //setupWithNavController(bottomNavigationView, navController)

       // Log.i("ciao","$navController")
        //bottomNavigationView.setupWithNavController(navController)



       /* var btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            Intent(this, Login_Activity::class.java).also{
                startActivity(it)
            }

        }*/


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
            startActivity(Intent(this, Login_Activity::class.java))
            finish()
        }
    }

    //a method for signOut, it may be call form a FRAGMENT
    internal fun signOut(){
        firebaseAuth.signOut()
        checkUser()
    }

    internal fun getDataOfUser(emailTextView: TextView){
        val firebaseUser=firebaseAuth.currentUser
        if (firebaseUser != null) {
            emailTextView.text = firebaseUser.email.toString()
        }

    }
}
