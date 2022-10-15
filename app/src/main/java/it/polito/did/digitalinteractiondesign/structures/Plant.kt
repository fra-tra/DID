package it.polito.did.digitalinteractiondesign.structures

import android.media.Image

data class Plant(var name : String,var image:String){



    var isDead : Boolean = false
        get() {return field}
        set(value) {field=value}

    var categoryPlant : String = ""
        get() {return field}
        set(value) {field=value}
    var causeDeath : String = ""
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
    var light :Boolean=false
        get() {return field}
        set(value) {field=value}
    var pumpWater:Boolean=false
        get() {return field}
        set(value) {field=value}
    var statusAutoWater:Boolean=false
        get() {return field}
        set(value) {field=value}




    var waterMeasuresReferences : Array<Double> = arrayOf(0.0, 0.0, 0.0, 0.0)

    var daysLived: Int=0
        get() {return field}
        set(value) {field=value}
    var dayDied: String=""
        get() {return field}
        set(value) {field=value}
    var dayBorn: String=""
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
    var lastWateredDate:String=""
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

    var macAddressHWPlants:String=""
            get(){return field}
            set(value) {field=value}
    var brightness:Int=0
        get(){return field}
        set(value) {field=value}


    constructor(name : String,image: String,room :String, vaseType :String,vaseSize:String,soilType:String,
                info:String, tips:String,category: String, dayBorned:String) : this(name, image) {
        idIdentification= category + "_" +dayBorned

        //set param
        this.room=room
        this.vaseType=vaseType
        this.vaseSize=vaseSize
        this.soilType=soilType
        this.information=info
        this.advice=tips
        this.categoryPlant=category
        this.dayBorn=dayBorned
    }
}


