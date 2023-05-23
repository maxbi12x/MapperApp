package com.example.mapperapp.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mapperapp.models.MarkerImagesModel

@Dao
interface MarkerImagesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMarkerImage(markerImagesModel: MarkerImagesModel): Long
    @Query("delete from marker_images where imageId=:Id")
    suspend fun deleteMarkerImagesUsingImageID(Id: Int)

    @Query("delete from marker_images where markerId=:Id")
    suspend fun deleteMarkerImagesUsingMarkerID(Id: Int) : Int

    @Query("select * from marker_images where markerId=:Id")
    suspend fun getMarkerImagesFromMarkerID(Id: Int): List<MarkerImagesModel>

}