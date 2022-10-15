package it.polito.did.digitalinteractiondesign.structures

import it.polito.did.digitalinteractiondesign.structures.Plant

data class Room(
    var name: String,
    var plants: MutableList<Plant> = arrayListOf(),
    var isGraveyard: Boolean = false
)
