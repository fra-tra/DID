package it.polito.did.digitalinteractiondesign

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class Weather : Fragment() {

        val CITY: DatabaseReference = FirebaseDatabase.getInstance().getReference("users").child("${ManagerFirebase.refereceDBUser}")
        val API: String="eef71f4b9a4082457323b5243822ca42"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherTask().execute()

    }
        inner class weatherTask() : AsyncTask<String, Void, String>() {
            override fun onPreExecute() {
                super.onPreExecute()
                /* Showing the ProgressBar, Making the main design GONE */

            }

       override fun doInBackground(vararg params: String?): String? {
                var response:String?
                try{
                    response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
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
                    val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                    val temp = main.getString("temp")+"°C"
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


                    /* Views populated, Hiding the loader, Showing the main design */


                } catch (e: Exception) {

                }

            }

            fun execute() {
                TODO("Not yet implemented")
            }
        }
    }
