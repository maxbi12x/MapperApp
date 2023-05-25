package com.example.mapperapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Application): ViewModel() {
    private var repository: Repository
    var markerListLive : LiveData<List<MarkerModel>>
    var imageLive : LiveData<ImageDetailsModel>
    var markerLive : LiveData<MarkerModel>

    init {
        repository = Repository.getInstance(context)
        markerListLive = repository.markerListLive
        imageLive = repository.imageLive
        markerLive = repository.markerLive
    }
    fun getImage(id : Int){
        viewModelScope.launch (Dispatchers.IO){ repository.getImage(id) }
    }
    fun addMarker(markerModel: MarkerModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMarker(markerModel)
            repository.alterMarkerCount(markerModel.imageId,true)
            repository.getMarkers(markerModel.imageId)
            repository.getImagesList()
        }
    }

    fun deleteMarkerUsingMarkerId(markerModel: MarkerModel){
        val id = markerModel.markerId
        val image = markerModel.imageId
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMarkerUsingMarkerId(id)
            repository.alterMarkerCount(image,false)
            repository.getMarkers(image)
            repository.getImagesList()
        }
    }
    fun getMarkers(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMarkers(id)
        }
    }
    fun getMarker(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMarker(id)
        }
    }
    fun updateMarker(markerModel: MarkerModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMarker(markerModel)
            repository.getMarker(markerModel.markerId)
        }
    }





}