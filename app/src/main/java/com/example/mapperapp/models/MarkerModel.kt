package com.example.mapperapp.models

data class MarkerModel(
    val xCord : Float,
    val yCord : Float,
    val person : String,
    val Title : String,
    val Discussion : String,
    val images : ArrayList<String>
)