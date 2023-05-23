package com.example.mapperapp.repository

import android.content.Context
import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.models.MarkerImagesModel
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.roomDB.AppDatabase
import com.example.mapperapp.roomDB.dao.MarkerDao

class Repository(context : Context) {

    private var appDatabase : AppDatabase
    private var _imagesListLive : MutableLiveData<List<ImageDetailsModel>> = MutableLiveData()
    val imagesListLive : LiveData<List<ImageDetailsModel>> = _imagesListLive
    private var _imageLive : MutableLiveData<ImageDetailsModel> = MutableLiveData()
    val imageLive : LiveData<ImageDetailsModel> = _imageLive
    private var _markerImagesListLive : MutableLiveData<List<MarkerImagesModel>> = MutableLiveData()
    val markerImagesListLive : LiveData<List<MarkerImagesModel>> = _markerImagesListLive
    private var _markerListLive : MutableLiveData<List<MarkerModel>> = MutableLiveData()
    val markerListLive : LiveData<List<MarkerModel>> = _markerListLive


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
            appDatabase.imageDetailsDao().decreaseMarkerCount(id)
        }else{
            appDatabase.imageDetailsDao().increaseMarkerCount(id)
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
    suspend fun deleteMarkerUsingMarkerId(id: Int){
        appDatabase.markerDao().deleteMarkerUsingMarkerID(id)
    }
    suspend fun  deleteMarkerUsingImageId(id:Int){
        appDatabase.markerDao().deleteAllMarkersUsingImageID(id)
    }
    suspend fun updateMarker(markerModel: MarkerModel){
        appDatabase.markerDao().updateMarker(markerModel)
    }
    suspend fun getMarkerImages(id: Int){
        //post
        _markerImagesListLive.postValue(appDatabase.markerImagesDao().getMarkerImagesFromMarkerID(id))

    }
    suspend fun addMarkerImage(markerImagesModel: MarkerImagesModel){
        appDatabase.markerImagesDao().insertMarkerImage(markerImagesModel)
    }
    suspend fun deleteMarkerImagesUsingMarkerId(id : Int){
        appDatabase.markerImagesDao().deleteMarkerImagesUsingMarkerID(id)
    }
    suspend fun deleteMarkerImagesUsingImageId(id : Int){
        appDatabase.markerImagesDao().deleteMarkerImagesUsingImageID(id)
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