package it.polito.did.digitalinteractiondesign.structures

import android.media.Image
data class User(var name : String?,var email: String?, var country: String? , var city: String?){
    var abilityLevel:String=""
        get() {return field}
        set(value) {field=value}
    var dedicationLevel :String=""
        get() {return field}
        set(value) {field=value}

}