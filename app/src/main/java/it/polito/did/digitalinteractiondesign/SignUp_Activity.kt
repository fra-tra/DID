package it.polito.did.digitalinteractiondesign

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.digitalinteractiondesign.databinding.ActivitySignUpBinding
import org.w3c.dom.Text

class SignUp_Activity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivitySignUpBinding
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
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var passwordTextIL = findViewById<TextInputLayout>(R.id.passwordTextIL)
        passwordTextIL.helperText="Password must be at least 6 characters long"

        //Configure Actionbar
        actionBar=supportActionBar!!
       // actionBar.title="Sign Up"
        actionBar.title=""
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


        // configure Progress Dialog
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        // progressDialog.setMessage("Creating account In...")
        progressDialog.setCanceledOnTouchOutside(false)
        // init firebase authentication
        firebaseAuth= FirebaseAuth.getInstance()
        // handle click, begin signup
        binding.signUpBtn.setOnClickListener {
            // validare data
            validateData()
        }


    }
    private fun validateData(){
        // get data
        email=binding.emailEditT.text.toString().trim()
        password=binding.passwordEditT.text.toString().trim()
        // validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
         //  binding.emailEditT.error="Invalid email format"
            binding.emailTextIL.error="Invalid email format"

        }else if(TextUtils.isEmpty(password)){
            //password isn't entered
         //  binding.passwordEditT.error="Please enter password"
            binding.emailTextIL.error=null
            binding.passwordTextIL.error="Please enter password"


        }else if(password.length<6){
            // password lenght is less than 6 char
         //   binding.passwordEditT.error="Password must at least 6 characters long"
            binding.emailTextIL.error=null
            binding.passwordTextIL.error="Password must at least 6 characters long"
        }else {
            // data is valid, continue signup
            firebaseSignUp()
        }
    }

    private fun firebaseSignUp() {
        //show progress
        progressDialog.show()
        // create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //signup success
                progressDialog.dismiss()
                // get current user
                val firebaseUser=firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()
                //open profile
                startActivity(Intent(this,Home_Activity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                // signup failed
                progressDialog.dismiss()
                Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
            }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previuos activity, when back button of actionbar clicked
        return super.onSupportNavigateUp()
    }
}