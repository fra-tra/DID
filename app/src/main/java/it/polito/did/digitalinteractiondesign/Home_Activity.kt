package it.polito.did.digitalinteractiondesign

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
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
        actionBar.title="Profile"

        //init firebase auth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //handle click, logout
        binding.logOutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        //configure navigation bar
        // this line hide Action Bar
        //supportActionBar.hide()

       // navigationView = this.findViewById(R.id.bottom_navigation)
       // supportFragmentManager.beginTransaction().replace(R.id.body_container, HomeFragment() ).commit()
        // navigationView.selectedItemId(R.id.nav_home)


}

    private fun checkUser() {
        //check user is logged or not
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            //user not null, user is logged in, get user info
            val email= firebaseUser.email
            // set to text view
            binding.emailTv.text=email
        }else {
            //user is null, user is not logged
            startActivity(Intent(this,Login_Activity::class.java))
            finish()
        }
    }
}
