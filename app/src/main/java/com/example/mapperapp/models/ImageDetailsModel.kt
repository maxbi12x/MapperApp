package com.example.mapperapp.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageDetailsModel(
    @PrimaryKey(autoGenerate = true)
    val imageId : Int = 0,
    val imageUri : Uri,
    val title : String,
    val timeAdded : Long,
    val markersCount : Int = 0
)
