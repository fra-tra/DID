package it.polito.did.digitalinteractiondesign.activity


import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.digitalinteractiondesign.ListPlants
import it.polito.did.digitalinteractiondesign.ManagerFirebase
import it.polito.did.digitalinteractiondesign.ManagerPlants
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ActivityHomeBinding
import it.polito.did.digitalinteractiondesign.fragments.Home
import it.polito.did.digitalinteractiondesign.structures.PlantHomeSummaryAdapter
import it.polito.did.digitalinteractiondesign.structures.User
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class Home_Activity : AppCompatActivity() {
    companion object{
        var nameUserStatic = ""
        var emailUserStatic = ""
        var countryUserStatic = ""
        var cittyUserStatic = ""
        var temperaturaByCity=""
        val API: String="eef71f4b9a4082457323b5243822ca42"
    }
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

        val userTemp=getUserFromSignUp()
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
            // extract data from intent
            val signUpUser = intent.getBooleanExtra("SignUp",false)
            if(signUpUser){
                getUserFromSignUp()?.let { ManagerFirebase.addUserToDBRealTime(it) }
            }

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
            emailTextView.text =firebaseUser.email.toString()

        }

    }

     fun getUserFromSignUp() : User {
         nameUserStatic = intent.getStringExtra("NameUser").toString()
         emailUserStatic = intent.getStringExtra("EmailUser").toString()
         countryUserStatic = intent.getStringExtra("CountryUser").toString()
         cittyUserStatic = intent.getStringExtra("CityUser").toString()


        val userTemp=User(nameUserStatic, emailUserStatic, countryUserStatic, cittyUserStatic)
        return userTemp
    }//[m] getUserFromSignUp()


}




