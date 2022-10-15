package it.polito.did.digitalinteractiondesign

import it.polito.did.digitalinteractiondesign.structures.Plant

class ListPlants {
    var listPlants =  mutableListOf<Plant>()

    fun addPlantInList(plant:Plant){
        listPlants.add(plant)
    }

    override fun toString(): String {
        return listPlants.toString()
    }
    fun isEmpty() : Boolean{
        var tempBool : Boolean

        if (listPlants.isEmpty()){
            tempBool=true
        }else{
            tempBool=false
        }

        return  tempBool
    }
}