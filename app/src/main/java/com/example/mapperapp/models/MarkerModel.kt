package com.example.mapperapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class MarkerModel(
    @PrimaryKey(autoGenerate = true)
    val markerId : Int = 0,
    val imageId : Int,
    val xCord : Float,
    val yCord : Float,
    val person : String,
    val title : String,
    val discussion : String
)