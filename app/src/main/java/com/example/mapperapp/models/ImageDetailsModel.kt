package com.example.mapperapp.models

data class ImageDetailsModel(
    val imageUri : String,
    val title : String,
    val timeAdded : String,
    val markersCount : Int
//    ,val markers : ArrayList<MarkerModel>
)
