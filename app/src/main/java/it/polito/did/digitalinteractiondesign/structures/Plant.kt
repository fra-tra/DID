package it.polito.did.digitalinteractiondesign.structures

import android.media.Image

data class Plant(
    var name : String,
    var image : Image?,
    var isDead : Boolean
)