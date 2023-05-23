package com.example.mapperapp.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mapperapp.models.MarkerModel

@Dao
interface MarkerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMarker(markerModel: MarkerModel): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateMarker(markerModel: MarkerModel): Int

    @Query("delete from markers where markerId=:marker")
    suspend fun deleteMarkerUsingMarkerID(marker: Int): Int

    @Query("delete from markers where imageId=:image")
    suspend fun deleteAllMarkersOfDrawingWithID(image: Int)

    @Query("select count(*) from markers where imageId=:id")
    fun getMarkerCountOf(id: Int): Int

    @Query("select * from markers where imageId=:id")
    fun getMarkersUsingImageID(id: Int): List<MarkerModel>
}