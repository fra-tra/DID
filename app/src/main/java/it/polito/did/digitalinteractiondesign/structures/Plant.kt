package it.polito.did.digitalinteractiondesign.structures

import android.media.Image

data class Plant(var name : String,var image:Image?){



    var isDead : Boolean = false
        get() {return field}
        set(value) {field=value}

    //data From Real time Database
    var humidity: Double=0.0
        get() {return field}
        set(value) {field=value}
    var waterLevelMeasure : Double = 0.0
        get() {return field}
        set(value) {field=value}
    var temperature: Int=0
        get() {return field}
        set(value) {field=value}



    var waterMeasuresReferences : Array<Double> = arrayOf(0.0, 0.0, 0.0, 0.0)

    var daysLived: Int=0
        get() {return field}
        set(value) {field=value}
    var timesWetted :Int=0
        get() {return field}
        set(value) {field=value}
    var information: String=""
        get() {return field}
        set(value) {field=value}
    var advice: String=""
        get() {return field}
        set(value) {field=value}
    // SWITCH CALENDARIZZAZIONE
    var switchStatus : Boolean=false
        get() {return field}
        set(value) {field=value}
    var dataInitCalendar: String=""
        get() {return field}
        set(value) {field=value}
    var dataFinshCalendar: String=""
        get() {return field}
        set(value) {field=value}

    //Data Registration

    var room :String=""
        get() {return field}
        set(value) {field=value}
    var vaseType :String=""
        get() {return field}
        set(value) {field=value}
    var vaseSize:String=""
        get() {return field}
        set(value) {field=value}
    var soilType:String=""
        get() {return field}
        set(value) {field=value}
    var idIdentification :String=""
        get() {return field}
        set(value) {field=value}


    constructor(name : String,image: Image?,room :String, vaseType :String,vaseSize:String,soilType:String) : this(name, image) {
         idIdentification= name + "_" +room

        //set param
        this.room=room
        this.vaseType=vaseType
        this.vaseSize=vaseSize
        this.soilType=soilType
    }
}

   


