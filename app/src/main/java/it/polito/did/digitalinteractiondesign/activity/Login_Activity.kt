package it.polito.did.digitalinteractiondesign.activity

import android.animation.Animator
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.digitalinteractiondesign.R
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
        actionBar.hide()

        //configure statusBar
        window.statusBarColor = resources.getColor(R.color.light_green)


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
            //before logIn in, validate data
            //get data
            email=binding.emailEditT.text.toString().trim()
            password=binding.passwordEditT.text.toString().trim()

            // validate
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                //invalid email format
              // binding.emailEditT.setError("Invalid email format")
                 binding.emailTextIL.error = "Invalid email format"

            }else if(TextUtils.isEmpty(password)){
                //no password entered
                //binding.passwordEditT.error="Please enter password"
                binding.passwordTextIL.error = "Please enter password"
                binding.emailTextIL.error = null
            }else{
                //data is validated, begin login
                binding.passwordTextIL.error = null
                binding.emailTextIL.error = null
                firebaseLogin()
            }

        }


        var emailEditT = findViewById<EditText>(R.id.emailEditT)
        var passwordEditT = findViewById<EditText>(R.id.passwordEditT)

       /* if(emailEditT.requestFocus()) {
            Log.d("prova focus", "errore")
        }
        emailEditT.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            @Override
            public override fun afterTextChanged(s: Editable?) {
                // TODO Auto-generated method stub
                // doSomething();
                Log.d("debug focus", s.toString())
            }

        })*/


       //da verificare se memorizza contenuto email
       emailEditT.setOnFocusChangeListener(object: View.OnFocusChangeListener {

            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (hasFocus) {

                } else {

                    if(!Patterns.EMAIL_ADDRESS.matcher(emailEditT.text.toString()).matches() && !TextUtils.isEmpty(emailEditT.text.toString())){
                        binding.emailTextIL.error = "Invalid email format"
                    }
                    else {
                        binding.emailTextIL.error = null
                    }
                }
            }
        });

        passwordEditT.setOnFocusChangeListener(object: View.OnFocusChangeListener {

            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (hasFocus) {

                } else {

                    if(TextUtils.isEmpty(passwordEditT.text.toString()) && !TextUtils.isEmpty(emailEditT.text.toString())){
                        binding.passwordTextIL.error = "Please enter password"
                        Log.d("password",  passwordEditT.text.toString())
                        Log.d("email",  passwordEditT.text.toString())
                    }
                    else {
                        binding.passwordTextIL.error = null
                    }
                }
            }
        });






        //animation setup
        var animInizio = findViewById<LottieAnimationView>(R.id.login_beginning)
        animInizio.setAnimation(R.raw.login_beginning_cropped)
        animInizio.loop(false)
        animInizio.isVisible = true
        animInizio.playAnimation()

        var animLoop = findViewById<LottieAnimationView>(R.id.login_loop)
        animLoop.setAnimation(R.raw.login_loop_cropped)
        animLoop.loop(true)
        animLoop.isInvisible = true


        animInizio.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                animLoop.isInvisible = false
                animInizio.isVisible = false
                animLoop.playAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })



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
               Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()

            }
    }
}