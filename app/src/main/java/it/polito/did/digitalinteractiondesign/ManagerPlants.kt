package it.polito.did.digitalinteractiondesign

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import it.polito.did.digitalinteractiondesign.structures.Plant


class ManagerPlants {
companion object{
    val myPlants = mutableListOf <Plant>()
    lateinit var deadPlants: MutableList <Plant>

    var db = FirebaseFirestore.getInstance()


    fun addPlantDB(tempDB:String,tempPlant: Plant){
        //create struct to register plant
        val plant: MutableMap<String, Any> = HashMap()
        plant["Name"] = tempPlant.name
        plant["Room"] = tempPlant.room
        plant["Vase Type"] = tempPlant.vaseType
        plant["Vase Size"] = tempPlant.vaseSize
        plant["Soil Type"] = tempPlant.soilType
        plant["Days Lived"]=tempPlant.daysLived
        plant["Times Wetted"]=tempPlant.timesWetted
        plant["Information"] = tempPlant.information
        plant["Advice"] = tempPlant.advice
        plant["Calendar is ON"]=tempPlant.switchStatus
        plant["Date Init Calendar"]=tempPlant.dataInitCalendar
        plant["Date Finish Calendar"]=tempPlant.dataFinshCalendar




        // test plant add object

        val plantTemp:Plant=tempPlant

        // Add a new document with a generated ID
        // Add a new document with a generated ID
        db.collection("Plants").document("${tempDB}").collection("Plant").document("${tempPlant.idIdentification}")
            .set(plant)
            .addOnSuccessListener {


                //add Plant in list
                myPlants.add(0,tempPlant)
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + tempPlant.idIdentification
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }

    fun updatePlantDB(){

    }
    fun getPlantsFromDB(){

    }

    fun verifyPlantExist(){



    }
}






}