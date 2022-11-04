package it.polito.did.digitalinteractiondesign

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import it.polito.did.digitalinteractiondesign.structures.Plant
import it.polito.did.digitalinteractiondesign.structures.User
import java.time.Duration
import java.time.LocalDateTime


class ManagerFirebase {
companion object{
    //reference to current User, aggiornato ogni volta che chiamo il metodo
    var currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    var refereceDBUser=currentUser?.uid




    val dataBase = FirebaseDatabase.getInstance().getReference("users")

    // Real Time reference
    var referenceDBAlive= dataBase.child("${refereceDBUser}").child("plants").child("Alive")
    var referenceDBDead = dataBase.child("${refereceDBUser}").child("plants").child("Dead")
    var referenceDBPlants= dataBase.child("${refereceDBUser}").child("plants")


    fun addPlantInDB(dbType: String,plant: Plant){
        when(dbType){
            "Alive"->{
                referenceDBAlive.child(plant.idIdentification.toString()).setValue(hasMapPlant(plant))
            }
            "Dead"->{
                referenceDBDead.child(plant.idIdentification.toString()).setValue(hasMapPlant(plant))
            }else ->{
                Log.i("ERROR", "Reinsert dbType, first param")
            }
        }
    }//[m] addPlantInDB(dbType: String,plant: Plant)

    fun deletePlantInDB(dbType: String,plant: Plant){

        when(dbType){
            "Alive"->{
                //referenceDBAlive.child(plant.idIdentification).setValue(hasMapPlant(plant))
                referenceDBAlive.child(plant.idIdentification).removeValue()
                addPlantInDB("Dead",plant)
            }
            "Dead"->{
                referenceDBDead.child(plant.idIdentification).removeValue()
                addPlantInDB("Alive",plant)

            }else ->{
            Log.i("ERROR", "Reinsert dbType, first param")
        }
        }
    }//[m] deletePlantInDB(dbType: String,plant: Plant)



    fun hasMapPlant(tempPlant:Plant): MutableMap<String, Any>{
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

        //Physics
        plant["Humidity"]=tempPlant.humidity
        plant["Temperature"]=tempPlant.temperature
        plant["WaterLevel"]=tempPlant.waterLevelMeasure
        plant["Light"]=tempPlant.light
        plant["IdPlant"]=tempPlant.idIdentification
        plant["Day Born"]=tempPlant.dayBorn
        plant["Day Died"]=tempPlant.dayDied
        plant["Is Dead"] = tempPlant.isDead
        plant["Category Plant"]=tempPlant.categoryPlant
        plant["Cause Death"]=tempPlant.causeDeath
        plant["Last Watered Date"]=tempPlant.lastWateredDate
        plant["Image Url"]=tempPlant.image
        plant["Mac Address"]=tempPlant.macAddressHWPlants
        plant["Brightness"]=tempPlant.brightness
        plant["Pump Water"]=tempPlant.pumpWater
        plant["Status Auto Water"]=tempPlant.statusAutoWater

        return plant
    }
    fun fromHashMapToPlant(hashPlant: HashMap<String, Any?>):Plant{
      val tempPlant= Plant("Name","imageMissing")
        tempPlant.name = hashPlant.getValue("Name") as String
        tempPlant.room= hashPlant.getValue("Room") as String
        tempPlant.vaseType= hashPlant.getValue("Vase Type") as String
        tempPlant.vaseSize = hashPlant.getValue("Vase Size") as String
        tempPlant.soilType= hashPlant.getValue("Soil Type") as String
        tempPlant.daysLived = hashPlant.getValue("Days Lived").toString().toInt()
        tempPlant.timesWetted= hashPlant.getValue("Times Wetted").toString().toInt()
        tempPlant.information  = hashPlant.getValue("Information") as String
        tempPlant.advice = hashPlant.getValue("Advice") as String
        tempPlant.switchStatus = hashPlant.getValue("Calendar is ON") as Boolean
        tempPlant.dataInitCalendar= hashPlant.getValue("Date Init Calendar") as String
        tempPlant.dataFinshCalendar= hashPlant.getValue("Date Finish Calendar") as String

        //Physics
        tempPlant.humidity= hashPlant.getValue("Humidity").toString().toDouble()
        tempPlant.temperature= hashPlant.getValue("Temperature").toString().toInt()
        tempPlant.waterLevelMeasure= hashPlant.getValue("WaterLevel").toString().toDouble()
        tempPlant.light= hashPlant.getValue("Light") as Boolean
        tempPlant.idIdentification=hashPlant.getValue("IdPlant").toString()
        tempPlant.brightness=hashPlant.getValue("Brightness").toString().toInt()
        //Time Lived
        tempPlant.dayBorn=hashPlant.getValue("Day Born").toString()
        tempPlant.dayDied=hashPlant.getValue("Day Died").toString()
        tempPlant.isDead=hashPlant.getValue("Is Dead").toString().toBoolean()
        tempPlant.categoryPlant=hashPlant.getValue("Category Plant").toString()
        tempPlant.causeDeath=hashPlant.getValue("Cause Death").toString()
        tempPlant.lastWateredDate=hashPlant.getValue("Last Watered Date").toString()
        tempPlant.image=hashPlant.getValue("Image Url").toString()
        tempPlant.macAddressHWPlants=hashPlant.getValue("Mac Address").toString()
        tempPlant.pumpWater=hashPlant.getValue("Pump Water").toString().toBoolean()
        tempPlant.statusAutoWater=hashPlant.getValue("Status Auto Water").toString().toBoolean()


        return tempPlant
    }
    fun hasMapUser(tempUser:User): MutableMap<String, Any?> {
        val user: MutableMap<String, Any?> = HashMap()
        user["Name"] = if(tempUser.name!=null) tempUser.name else "StandardUser"
        user["Email"] = tempUser.email
        user["Country"]=if(tempUser.country!=null) tempUser.country else "Missing info!"
        user["City"] = if(tempUser.city!=null) tempUser.city else "Missing info!"
        user["Ability Level"]=if(tempUser.abilityLevel!=null) tempUser.abilityLevel+">" else "Missing info! >"
        user["Dedication Level"]=if(tempUser.dedicationLevel!=null) tempUser.dedicationLevel+">" else "Missing info! >"

        return user
    }
    fun fromHashMapToUser(hasMapUser:MutableMap<String, Any?>) : User{
        val tempUser= User("","","","")

        tempUser.name =hasMapUser.getValue("Name").toString()
        tempUser.city=hasMapUser.getValue("City").toString()
        tempUser.country=hasMapUser.getValue("Country").toString()
        tempUser.email=hasMapUser.getValue("Email").toString()
        tempUser.dedicationLevel=hasMapUser.getValue("Ability Level").toString()
        tempUser.abilityLevel=hasMapUser.getValue("Dedication Level").toString()

        return tempUser
    }



        fun addUserToDBRealTime( userTemp:User){
           updateCurrentUserReference()

           dataBase.child("${refereceDBUser}").setValue(hasMapUser(userTemp))
           dataBase.child("${refereceDBUser}").child("plants").child("Alive")
           dataBase.child("${refereceDBUser}").child("plants").child("Died")
           // Log.d("Utente registrato " ,"${refereceDBUser}")
        }//[m]addUserToDBRealTime()

        fun updateCurrentUserReference(){
            currentUser = FirebaseAuth.getInstance().currentUser
            refereceDBUser=currentUser?.uid
        }//[m]updateCurrentUser()

        fun updateValueUser(positionTemp:String, value:String){
            dataBase.child("${refereceDBUser}").child(positionTemp).setValue(value)

        }

        fun updateValuePlantAlive(idPlant:String, positionTemp: String, value:String){
            // UPDATE BY NAME


                if(positionTemp=="Light"  || positionTemp=="Pump Water" || positionTemp=="Status Auto Water" ){
                    referenceDBAlive.child(idPlant).child(positionTemp).setValue(value.toBoolean())
                }else{
                    referenceDBAlive.child(idPlant).child(positionTemp).setValue(value)
                }


            }







    @RequiresApi(Build.VERSION_CODES.O)
    fun daysLived( d1:String,  d2:String):String{
        val date1 = LocalDateTime.parse(d1.toString()+"T20:00:00.0400")
        val date2 = LocalDateTime.parse(d2.toString()+"T20:00:00.0400")

            return Duration.between(date1, date2).toDays().toString()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun testFormatter(){
        val strDatewithTime = LocalDateTime.now().toString()
        val dateTime = LocalDateTime.parse(strDatewithTime)

        Log.d("PasT",dateTime.toString().split("T")[0])
    }
    }




}

