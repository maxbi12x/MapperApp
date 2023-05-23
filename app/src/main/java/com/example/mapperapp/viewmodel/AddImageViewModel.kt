package com.example.mapperapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddImageViewModel(context: Application): ViewModel() {
    private var repository: Repository
    var imagesListLive : LiveData<List<ImageDetailsModel>>
    var imageLive : LiveData<ImageDetailsModel>

    init {
        repository = Repository.getInstance(context)
        imagesListLive = repository.imagesListLive
        imageLive = repository.imageLive
    }

    fun imagesList(){
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.getImagesList()
        }
    }
    fun getImage(id : Int){
        viewModelScope.launch (Dispatchers.IO){ repository.getImage(id) }
    }
    fun deleteImage(id : Int){
        viewModelScope.launch (Dispatchers.IO){
            repository!!.deleteImageByImageId(id)
            repository!!.deleteMarkerUsingImageId(id)
            repository!!.deleteMarkerImagesUsingImageId(id)
            repository!!.getImagesList()
        }
    }
    fun addImage(imageDetailsModel: ImageDetailsModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository!!.addImage(imageDetailsModel)
            repository!!.getImagesList()

        }
    }
    fun updateImageName(imageDetailsModel : ImageDetailsModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateImageName(imageDetailsModel)
            repository.getImagesList()
        }

    }

}