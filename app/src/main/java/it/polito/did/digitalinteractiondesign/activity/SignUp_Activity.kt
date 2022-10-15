package it.polito.did.digitalinteractiondesign.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.digitalinteractiondesign.R
import it.polito.did.digitalinteractiondesign.databinding.ActivitySignUpBinding
import it.polito.did.digitalinteractiondesign.structures.User


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

    private var country =""
    private var city=""

    private var isSignup: Boolean=false

    var userTemp : User? = null

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

        //controllo email e password ui
        var emailEditT = findViewById<EditText>(R.id.emailEditT)
        var passwordEditT = findViewById<EditText>(R.id.passwordEditT)


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

                    if(TextUtils.isEmpty(passwordEditT.text.toString()) && emailEditT.text.toString() != ""){
                        binding.passwordTextIL.error = "Please enter password"
                        Log.d("email",  email)
                    }
                    else {
                        binding.passwordTextIL.error = null
                    }
                }
            }
        });



        //definisco array di stringhe inizialmente vuoto che dovrà essere popolato con i paesi
        val countries = ArrayList<String>()

        //inserisco tre paesi come prova nell'array
        countries.add("Italy")
        countries.add("Argentina")
        countries.add("Netherlands")

        //lego i paesi dell'array alle opzioni del dropdown menu (riutilizzo il layout gia usato per il dropdown menu del vase type)
        val countryArrayAdapter = ArrayAdapter(this, R.layout.item_dropdown_vase_settings, countries)
        binding.tvCountry.setAdapter(countryArrayAdapter)

        // nascondo la soft keyboard quando il focus è sul dropdown menu del paese
        binding.tvCountry.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
            if (b){
                Log.d("prova", "hide keyboard")
                  val manager: InputMethodManager = getSystemService(
                   Context.INPUT_METHOD_SERVICE
               ) as InputMethodManager
               manager
                   .hideSoftInputFromWindow(
                       view.windowToken, 0
                   )

            }
        }


        //processo analogo a countries
        val cities = ArrayList<String>()
        cities.add("Torino")
        cities.add("Milano")
        cities.add("Firenze")
        val cityArrayAdapter = ArrayAdapter(this, R.layout.item_dropdown_vase_settings, cities)
        binding.cityEditT.threshold=1;
        binding.cityEditT.setAdapter(cityArrayAdapter)

        //dipendenza tra country e city: city è disabled a meno che non venga inserito un valore in country
        var selectedCountry : String
        binding.tvCountry.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->

                //elimina selezione di city qualora sia presenta quando seleziono un paese
               binding.cityEditT.setText("");

                //abilita selezione di city quando seleziono un paese
               selectedCountry = countryArrayAdapter.getItem(position).toString()
               if(selectedCountry != "") {
                   binding.cityTextIL.isEnabled = true
               }
            }
    }

    private fun validateData(){
        // get data
        email=binding.emailEditT.text.toString().trim()
        password=binding.passwordEditT.text.toString().trim()
        country=binding.tvCountry.text.toString()
        city=binding.cityEditT.text.toString()
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
            //register uid in db realtime
            userTemp= User(null,email,country,city)
           // ManagerFirebase.addUserToDBRealTime(userTemp!!)
            Log.d("Utente registrato " ,"IN USERS - SignUp")
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

                val  intent = Intent(this, Home_Activity::class.java).also{
                    isSignup=true

                    it.putExtra("SignUp",isSignup)
                    it.putExtra("NameUser", userTemp?.name)
                    it.putExtra("EmailUser", userTemp?.email)
                    it.putExtra("CountryUser", userTemp?.country)
                    it.putExtra("CityUser", userTemp?.city)
                    startActivity(it)
                    finish()
                }

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