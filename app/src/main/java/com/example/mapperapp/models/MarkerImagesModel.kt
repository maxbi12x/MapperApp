package com.example.mapperapp.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marker_images")
data class MarkerImagesModel(
    @PrimaryKey(autoGenerate = true)
    val markerImageId : Int = 0,
    val imageId : Int,
    val markerID : Int,
    val imageUri : Uri
)
