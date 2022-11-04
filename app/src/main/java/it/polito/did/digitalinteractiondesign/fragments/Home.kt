package it.polito.did.digitalinteractiondesign.fragments

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi

import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.polito.did.digitalinteractiondesign.*
import it.polito.did.digitalinteractiondesign.activity.Home_Activity
import it.polito.did.digitalinteractiondesign.structures.PlantHomeSummaryAdapter
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var minTemperatureDanger = 10
    var minTemperatureWarning = 15

companion object{
    var countryUserStatic = Home_Activity.countryUserStatic
    var cittyUserStatic = ""
    var temperaturaByCity=""
    val API: String="eef71f4b9a4082457323b5243822ca42"

}




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        //Hide action bar from fragment
       // (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var cityTv=view.findViewById<TextView>(R.id.city)
        var countryTv=view.findViewById<TextView>(R.id.country)


        // test firestore
        ManagerPlantsInfoFirestore.getAllPlantInCollection()
        ManagerPlantsInfoFirestore.getImageCategoryInCollection()
        ManagerPlantsInfoFirestore.getRoomImageInCollection()

        val viewModelDB = ViewModelProvider(this).get(ManagerPlants::class.java)

        viewModelDB.getPlantsFromDBRealtime("Alive")

        viewModelDB.returnListPlantsAlive().observe(viewLifecycleOwner, Observer {
            val aliveTempList=ListPlants()
            for((key,value) in it){
               // android.util.Log.i("HasMapPlants","$key=$value + ${it.values}")
               // android.util.Log.i("Plants","=$value ")
                val mapTemp : HashMap<String,Any?> = value as HashMap<String, Any?>
                if(mapTemp!=null){

                }
                var tempPlant = ManagerFirebase.fromHashMapToPlant(mapTemp)
                //android.util.Log.i("ListPlants","="+tempPlant.toString())
                aliveTempList.addPlantInList(tempPlant)
            }

            val adapter = PlantHomeSummaryAdapter(aliveTempList.listPlants)
            val rvPlantsHome = view.findViewById<RecyclerView>(R.id.rvPlantsHome)
            rvPlantsHome.adapter = adapter
            rvPlantsHome.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        })



        viewModelDB.getUserInfo(ManagerFirebase.refereceDBUser.toString())
        viewModelDB.returndListUserData().observe(viewLifecycleOwner,Observer{

            cityTv.text=it.get("City").toString()
            cittyUserStatic=it.get("City").toString()
            countryTv.text=it.get("Country").toString()
            countryUserStatic=it.get("Country").toString()
            weatherTask().execute()



        })



      //  val plantList : MutableList<Plant> = mutableListOf()



        val noPlantsInHomeGroup = view.findViewById<Group>(R.id.noPlantsInHomeGroup)
        val fabAddPlantFromHome = view.findViewById<FloatingActionButton>(R.id.btnAddPlantFromHome)
        val noPlantsInHomeTV = view.findViewById<TextView>(R.id.noPlantsInHomeTV)



        /*
        if(plantList.size > 0) {
            fabAddPlantFromHome.visibility = View.GONE
            noPlantsInHomeTV.visibility = View.GONE
        }

        else {
            fabAddPlantFromHome.visibility = View.VISIBLE
            noPlantsInHomeTV.visibility = View.VISIBLE
            fabAddPlantFromHome.setOnClickListener{
                val bottomNav: BottomNavigationView = (context as Home_Activity).findViewById(R.id.bottomNavigationView)
                bottomNav.selectedItemId = R.id.discover

            }
        }*/

        //button per passare da home a discover sempre presente
            val fabAddPlantFromHome2 = view.findViewById<FloatingActionButton>(R.id.btnAddPlantFromHome2)
            fabAddPlantFromHome2.setOnClickListener{



            val bottomNav: BottomNavigationView = (context as Home_Activity).findViewById(R.id.bottomNavigationView)
            bottomNav.selectedItemId = R.id.discover

        }
    }

    //general function to check if either temperature, humidity or brightness measures need to be alerted with a danger or warning icon
   /* private fun showMeasureAlert(progressBar: ProgressBar, imageToShow: ImageView, minMeasureDanger: Int, maxMeasureDanger: Int, minMeasureWarning: Int, maxMeasureWarning: Int) {
        if(progressBar.progress <= minMeasureDanger) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_danger)
        }
        else if (progressBar.progress <= minMeasureWarning) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_warning)
        }
        else if (progressBar.progress >= maxMeasureDanger) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_danger)
        }
        else if (progressBar.progress >= maxMeasureWarning) {
            imageToShow.isVisible = true
            imageToShow.setImageResource(R.drawable.ic_warning)
        }
        else {
            imageToShow.isVisible = false
        }


    }*/
    inner class weatherTask() : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */

        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=${cittyUserStatic}&units=metric&appid=${API}").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
                val temp = main.getString("temp").toDouble().roundToInt().toString()+"°C"
                val tempMin = "Min Temp: " + main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: " + main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */

                temperaturaByCity=temp
                var temperatureTV= view?.findViewById<TextView>(R.id.temperature)
                if (temperatureTV != null) {
                    temperatureTV.text= temp
                }

              //  Log.d("temperature", "${cittyUserStatic}=${temp}=${temperaturaByCity}")





            } catch (e: Exception) {

            }

        }


    }


}