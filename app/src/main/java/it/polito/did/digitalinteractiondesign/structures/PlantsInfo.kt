package it.polito.did.digitalinteractiondesign.structures

data class PlantsInfo(val name : String, val description: String, val wateringInterval: Int = 7, // how often the plant should be watered, in days
                      val imageUrl: String = "", val tips: String, val category: String,
                      val light: String, val idealTemperature: String, val humidity: String,
                      val difficulty: String) {






}



