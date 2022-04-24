package it.polito.did.digitalinteractiondesign.structures

import android.media.Image

data class Plant(
    var name : String,
    var image : Image?,
    var isDead : Boolean,

    // TEMPORARY HUMIDITY FORMAT
    var waterMeasure : Double = 0.0,
    var waterMeasuresReferences : Array<Double> = arrayOf(0.0, 0.0, 0.0, 0.0)
)