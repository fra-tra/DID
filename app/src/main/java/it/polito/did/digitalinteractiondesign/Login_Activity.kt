package it.polito.did.digitalinteractiondesign

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.digitalinteractiondesign.databinding.ActivityLoginBinding

class Login_Activity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityLoginBinding
    //ActionBar
    private lateinit var actionBar: ActionBar
    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog
    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // configure action bar
        actionBar=supportActionBar!!
        actionBar.title="Login"

        //configure progress dialog
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        //progressDialog.setMessage("Loggin In ...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebaseAuth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()
        // handle click, open register activity
        binding.noAccountTV.setOnClickListener {
            startActivity(Intent(this, SignUp_Activity::class.java))
        }

        // handle click, begin login
        binding.loginBtn.setOnClickListener {
            // before logIn in, validate data
            //get data
            email=binding.emailEditT.text.toString().trim()
            password=binding.passwordEditT.text.toString().trim()
            // validate
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                //invalid email format
                binding.emailEditT.setError("Invalid email format")

            }else if(TextUtils.isEmpty(password)){
                //no password entered
                binding.passwordEditT.error="Please enter password"
            }else{
                //data is validated, begin login
                firebaseLogin()
            }
        }
    }

    private fun checkUser(){
        // if user already logged in go to profile activity
        // get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null){
            //user is already logged in
            startActivity(Intent(this, Home_Activity::class.java))
            finish()
        }
    }
    private fun firebaseLogin(){
        // show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //get user info
                val firebaseUser= firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "LoggedIn as $email ", Toast.LENGTH_SHORT).show()
                //GOGOGOGOG
                startActivity(Intent(this, Home_Activity::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}