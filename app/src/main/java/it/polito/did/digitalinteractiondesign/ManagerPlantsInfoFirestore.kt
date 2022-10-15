package it.polito.did.digitalinteractiondesign

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import com.google.firebase.firestore.*
import it.polito.did.digitalinteractiondesign.structures.PlantsInfo

class ManagerPlantsInfoFirestore {
    companion object{
        var listPlantsInfoTEMP=ListPlantsInfo()
        var hasMapPlantByCategory : HashMap<String,ListPlantsInfo> = hashMapOf("Flowering plants" to ListPlantsInfo(),
            "Vegetables" to ListPlantsInfo(),"Herbs" to ListPlantsInfo(),"Leaf plants" to ListPlantsInfo(),"Cactus and Succulents" to ListPlantsInfo())

        var hasMapCategoryImage: HashMap<String,String> = hashMapOf("Flowering plants" to "","Vegetables" to "",
            "Herbs" to "","Leaf plants" to "","Cactus and Succulents" to "")
        var hasMapRoomsImage: HashMap<String,String> =hashMapOf("Balcony" to "","Bethroom" to "","Bedroom" to "",
            "Dining Room" to "","Garden" to "", "Kitchen" to "", "Living Room" to "")

        val dbFirestore=FirebaseFirestore.getInstance()



        val docRefPlantsInfo = dbFirestore.collection("plants")
        val docRefCategoryImage= dbFirestore.collection("imageCategoryPlants")
        val docRefRoomImage= dbFirestore.collection("imageRooms")


        // Source can be CACHE, SERVER, or DEFAULT.
        val source = Source.CACHE

        // Get the document, forcing the SDK to use the offline cache
        fun getAllPlantInCollection(){

            docRefPlantsInfo.orderBy("difficulty",Query.Direction.ASCENDING )
                .addSnapshotListener{ snapshots, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    for (dc in snapshots!!.documentChanges) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                          //  hashMapPlantInfo["Test"]?.toMutableList()?.add(dc.document.data as HashMap<String, Any>)
                          //  templistPlantInfo.add(dc.document.data as HashMap<String, Any>)
                            //hasMapPlantByCategory[dc.document.data.get("category").toString()]!!.listPlants!!.add(dc.document.data)

                            hasMapPlantByCategory[dc.document.data.get("category").toString()]?.addPlantInList(dc.document.data as HashMap<String, PlantsInfo?>)



                        }
                    }
                    //Log.i("Verifica", hasMapPlantByCategory.keys.toString() + "Chiavi")

                }



        }
        fun getImageCategoryInCollection(){

            docRefCategoryImage.orderBy("image Url",Query.Direction.ASCENDING )
                .addSnapshotListener{ snapshots, e ->
                    var templistPlantInfo : HashMap<String,Any> = HashMap()
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    for (dc in snapshots!!.documentChanges) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            //  hashMapPlantInfo["Test"]?.toMutableList()?.add(dc.document.data as HashMap<String, Any>)
                            //  templistPlantInfo.add(dc.document.data as HashMap<String, Any>)
                            //hasMapPlantByCategory[dc.document.data.get("category").toString()]!!.listPlants!!.add(dc.document.data)

                            hasMapCategoryImage[dc.document.id]=(dc.document.data.get("image Url") as String)



                        }
                    }
                    //Log.i("Verifica", hasMapCategoryImage.toString() + "Chiavi")

                }



        }
        fun getRoomImageInCollection(){

            docRefRoomImage.orderBy("image Url",Query.Direction.ASCENDING )
                .addSnapshotListener{ snapshots, e ->
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e)
                        return@addSnapshotListener
                    }
                    for (dc in snapshots!!.documentChanges) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            //  hashMapPlantInfo["Test"]?.toMutableList()?.add(dc.document.data as HashMap<String, Any>)
                            //  templistPlantInfo.add(dc.document.data as HashMap<String, Any>)
                            //hasMapPlantByCategory[dc.document.data.get("category").toString()]!!.listPlants!!.add(dc.document.data)

                            hasMapRoomsImage[dc.document.id]=(dc.document.data.get("image Url") as String)



                        }
                    }
                    Log.i("Verifica", hasMapRoomsImage.toString() + "Chiavi")

                }
        }

        fun fromHashMapToPlantInfo(hashPlant: MutableMap<String, PlantsInfo?>): PlantsInfo {
            val tempPlant= PlantsInfo(
                hashPlant.getValue("name") as String,
                hashPlant.getValue("description") as String,
                hashPlant.getValue("wateringInterval").toString().toInt() ,
                hashPlant.getValue("imageUrl") as String,
                hashPlant.getValue("tips") as String,
                hashPlant.getValue("category") as String,
                hashPlant.getValue("light") as String,
                hashPlant.getValue("idealTemperature") as String,
                hashPlant.getValue("humidity") as String,
                hashPlant.getValue("difficulty") as String)

            return tempPlant
        }

        fun returnPlantByCategoryByName(category: String, name: String):PlantsInfo{
            var plantInfoTemporanea = PlantsInfo("","",1,"","","","","","","")
            var listTemp : ListPlantsInfo? = hasMapPlantByCategory.get(category)
            Log.d("listaPiena", listTemp.toString())
            if (listTemp != null) {
                plantInfoTemporanea= listTemp.plantByName(name)
            }


            return plantInfoTemporanea

        }




}
}

