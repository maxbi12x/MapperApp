package com.example.mapperapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.models.MarkerImagesModel
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Application): ViewModel() {
    private var repository: Repository
    var markerImagesListLive : LiveData<List<MarkerImagesModel>>
    var markerListLive : LiveData<List<MarkerModel>>

    init {
        repository = Repository.getInstance(context)
        markerImagesListLive = repository.markerImagesListLive
        markerListLive = repository.markerListLive
    }
    fun addMarker(markerModel: MarkerModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMarker(markerModel)
            repository.alterMarkerCount(markerModel.imageId,true)
            repository.getImagesList()
        }
    }
    fun deleteMarkerUsingMarkerId(markerModel: MarkerModel){
        val id = markerModel.markerId
        val image = markerModel.imageId
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMarkerUsingMarkerId(id)
            repository.deleteMarkerImagesUsingMarkerId(id)
            repository.alterMarkerCount(image,false)
            repository.getImagesList()
        }
    }
    fun getMarkers(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMarkers(id)
        }
    }
    fun updateMarkers(markerModel: MarkerModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMarker(markerModel)
        }
    }
    fun getMarkerImages(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMarkerImages(id)
        }
    }
    fun addMarkerImage(markerImagesModel: MarkerImagesModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMarkerImage(markerImagesModel)
        }

    }





}