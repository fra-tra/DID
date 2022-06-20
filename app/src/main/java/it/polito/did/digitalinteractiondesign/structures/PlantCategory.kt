package it.polito.did.digitalinteractiondesign.structures

data class PlantCategory (
    var name: String,
    var plants: List<Plant> = arrayListOf(),

)

