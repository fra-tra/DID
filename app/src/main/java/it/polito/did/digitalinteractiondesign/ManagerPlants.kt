package it.polito.did.digitalinteractiondesign

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import it.polito.did.digitalinteractiondesign.structures.Plant


class ManagerPlants : ViewModel() {

    var myPlantsAlive = MutableLiveData<MutableMap<String, Any?>>()
    var myPlantsDied = MutableLiveData<MutableMap<String, Any?>>()
    val myUserData = MutableLiveData<MutableMap<String,Any?>>()
    var myPlants = MutableLiveData<MutableMap<String,Any?>>()

    fun returnListPlantsAlive():LiveData<MutableMap<String, Any?>>{
        return myPlantsAlive
    }
    fun returnListPlants():LiveData<MutableMap<String, Any?>>{
        return myPlants
    }
    fun returnListPlantsDied():LiveData<MutableMap<String, Any?>>{
        return myPlantsDied
    }

    fun returndListUserData():LiveData<MutableMap<String, Any?>>{
        return myUserData
    }
    fun addUserFromDB(mapUser: MutableMap<String, Any?>){
        myUserData.value= mapUser

    }

    fun addPlantAlive(mapPlants: MutableMap<String, Any?>){
          myPlantsAlive.value= mapPlants
          //Log.d("LISTA" ,"${myPlantsAlive.value?.size}")
    }

    fun addPlantDied(mapPlants: MutableMap<String, Any?>){
        myPlantsDied.value= mapPlants
        //Log.d("LISTA" ,"${myPlantsDied.value?.size}")
    }
    fun removePlant(dbTemp:String,plantTemp:Plant){
        when(dbTemp){
            "Alive"->{
               //Log.d("testRemove", myPlantsAlive.value?.keys.toString())
                myPlantsAlive.value?.remove(plantTemp.idIdentification)
            }
            "Dead"->{
                //myPlantsAlive.value?.remove(plantTemp.idIdentification)
            }
        }
    }








    fun getPlantsFromDBRealtime(stringaTemp :String) {
            // Update current User
           ManagerFirebase.updateCurrentUserReference()

            var dbPlantsReferenceAlive=ManagerFirebase.referenceDBAlive
            var dbPlantsReferenceDead=ManagerFirebase.referenceDBDead
            when(stringaTemp){
                "Alive"->{
                    dbPlantsReferenceAlive.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                             //________________________________________
                            // Prendi i dati che sono rappresentati da una mappa
                            if(dataSnapshot.getValue()!=null){
                                var plantsFromDB= dataSnapshot.getValue() as HashMap<String, Any>?

                                //aggiungo mappa delle piante del database ai livedata
                                myPlantsAlive.value?.clear()
                                addPlantAlive(plantsFromDB as MutableMap<String, Any?>)
                                Log.d("SAYME", "è successo qualcosa lista Vive")

                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("Erro_onCancelled","onCancelled", databaseError.toException())
                        }

                    })
                }
                "Dead"->{
                    dbPlantsReferenceDead.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            // quando il cimitero viene mosso, ci devono essere cambiamenti
                            if(dataSnapshot.getValue()!=null){
                                var plantsFromDB= dataSnapshot.getValue() as HashMap<String, Any>?

                                //aggiungo mappa delle piante del database ai livedata
                                myPlantsDied.value?.clear()
                                addPlantDied(plantsFromDB as MutableMap<String, Any?>)


                            }

                        }
                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.d("SAYME", "è successo qualcosa lista morte")

                        }

                    })
                }
            }



        }//[m]getPlantsFromDBRealtime

    fun getAllPlantsFromDB() {
        ManagerFirebase.updateCurrentUserReference()

        var dbRefencePlants=ManagerFirebase.referenceDBPlants

        dbRefencePlants.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var plantsToMapTemp :HashMap<String, Any> =HashMap()

                if(dataSnapshot.getValue()!=null){
                    plantsToMapTemp = dataSnapshot.getValue() as HashMap<String, Any>
                   for ((key,value) in plantsToMapTemp){
                       if(key=="Alive"){
                           addPlantAlive(plantsToMapTemp.get("Alive") as MutableMap<String, Any?>)
                       }else if ( key =="Dead"){
                           addPlantDied(plantsToMapTemp.get("Dead") as MutableMap<String, Any?>)
                       }
                   }

                }
                   // Log.d("ListaViva",myPlantsAlive.value?.size.toString() )
                    //Log.d("ListaMorta",myPlantsDied.value?.size.toString() )
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("SAYME", "è successo qualcosa lista morte")
            }

        })

    }
    fun getPlantByID(id:String):Plant{
    var plantTemp=Plant("","","","","","","","","","")
      //plantTemp= myPlantsAlive.value?.get(id) as Plant

        return plantTemp
    }

    fun getUserInfo(idUser:String){
        ManagerFirebase.updateCurrentUserReference()
       val referenceToUser =ManagerFirebase.dataBase.child(idUser)

        referenceToUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userToMapTemp :HashMap<String, Any> =HashMap()

                if(snapshot.getValue()!=null){
                    var userFromDB :HashMap<String, Any>? = snapshot.getValue() as HashMap<String, Any>?

                    if (userFromDB != null) {
                        for ((key,value ) in userFromDB){
                            if(key!="plants"){
                                //inserisco i valori nella mappa
                                userToMapTemp[key]=value
                            }

                        }
                    }
                        addUserFromDB(userToMapTemp as MutableMap<String, Any?>)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    companion object{
        var activePlantToView = String()


        fun assignActivePlant(tempIDPlant :String?){
            if(tempIDPlant!=null){
                activePlantToView=tempIDPlant
            }
        }



    }






}