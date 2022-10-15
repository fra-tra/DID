package it.polito.did.digitalinteractiondesign

import it.polito.did.digitalinteractiondesign.structures.Plant
import it.polito.did.digitalinteractiondesign.structures.PlantsInfo

class ListPlantsInfo{

    var listPlants : MutableSet<MutableMap<String,PlantsInfo?>> =  mutableSetOf(HashMap())

    fun addPlantInList(plant: HashMap<String, PlantsInfo?>){
        listPlants.add(plant)
    }

    fun plantByName(name: String):PlantsInfo{
        var plantInfoTemporanea = PlantsInfo("","",1,"","","","","","","")
        for (plant  in listPlants){
            if(!plant.isEmpty()){
                 plantInfoTemporanea = ManagerPlantsInfoFirestore.fromHashMapToPlantInfo(plant)
            }

            if(plantInfoTemporanea.name==name){
                return plantInfoTemporanea
            }
        }
        return plantInfoTemporanea
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