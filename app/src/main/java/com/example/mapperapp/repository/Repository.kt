package com.example.mapperapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.roomDB.AppDatabase

class Repository(context : Context) {

    private var appDatabase : AppDatabase
    private var _imagesListLive : MutableLiveData<List<ImageDetailsModel>> = MutableLiveData()
    val imagesListLive : LiveData<List<ImageDetailsModel>> = _imagesListLive
    private var _imageLive : MutableLiveData<ImageDetailsModel> = MutableLiveData()
    val imageLive : LiveData<ImageDetailsModel> = _imageLive
    private var _markerListLive : MutableLiveData<List<MarkerModel>> = MutableLiveData()
    val markerListLive : LiveData<List<MarkerModel>> = _markerListLive
    private var _markerLive : MutableLiveData<MarkerModel> = MutableLiveData()
    val markerLive : LiveData<MarkerModel> = _markerLive


    init {
        appDatabase = AppDatabase.getInstance(context)

    }
    suspend fun getImagesList(){

        _imagesListLive.postValue(appDatabase.imageDetailsDao().getImages())
    }

    suspend fun getImage(id: Int){
        _imageLive.postValue(appDatabase.imageDetailsDao().getSingleImage(id))
    }
    suspend fun deleteImageByImageId(id : Int){
        appDatabase.imageDetailsDao().deleteImage(id)
    }
    suspend fun addImage(imageDetailsModel: ImageDetailsModel){
        appDatabase.imageDetailsDao().insertImage(imageDetailsModel)

    }
    suspend fun alterMarkerCount(id: Int, inc : Boolean){
        if(inc){
            appDatabase.imageDetailsDao().increaseMarkerCount(id)
        }else{
            appDatabase.imageDetailsDao().decreaseMarkerCount(id)
        }
    }
    suspend fun updateImageName(imageDetailsModel: ImageDetailsModel){
        appDatabase.imageDetailsDao().updateImage(imageDetailsModel)
    }
    suspend fun addMarker(markerModel: MarkerModel){
        appDatabase.markerDao().insertMarker(markerModel)
    }
    suspend fun getMarkers(id : Int){
        //post
        _markerListLive.postValue(appDatabase.markerDao().getMarkers(id))
    }
    suspend fun getMarker(id : Int){
        _markerLive.postValue(appDatabase.markerDao().getMarker(id))
    }
    suspend fun deleteMarkerUsingMarkerId(id: Int){
        appDatabase.markerDao().deleteMarkerUsingMarkerID(id)
    }
    suspend fun  deleteMarkerUsingImageId(id:Int){
        appDatabase.markerDao().deleteAllMarkersUsingImageID(id)
    }
    suspend fun updateMarker(markerModel: MarkerModel){
        appDatabase.markerDao().updateMarker(markerModel)
    }




    companion object{
        @Volatile
        private var instance : Repository?= null
        @Synchronized
        fun getInstance(context: Context) : Repository{
            if(instance==null){
                instance  = Repository(context)
            }
            return instance!!
        }

    }
}