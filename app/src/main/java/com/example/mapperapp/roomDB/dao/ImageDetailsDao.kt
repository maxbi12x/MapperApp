package com.example.mapperapp.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mapperapp.models.ImageDetailsModel

@Dao
interface ImageDetailsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertImage(imageDetailsModel: ImageDetailsModel): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateImage(imageDetailsModel: ImageDetailsModel): Int

    @Delete
    suspend fun deleteImage(imageDetailsModel: ImageDetailsModel): Int

    @Query("update images set markerCount=markerCount-1 where imageId=:image")
    suspend fun decreaseMarkerCount(image: Int): Int

    @Query("update images set markerCount=markerCount-1 where imageId=:image")
    suspend fun increaseMarkerCount(image: Int): Int

    @Query("select * from images order by timeAdded desc")
    fun getImages(): List<ImageDetailsModel>


}