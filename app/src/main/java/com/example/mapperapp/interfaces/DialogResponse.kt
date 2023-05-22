package com.example.mapperapp.interfaces

import com.example.mapperapp.models.MarkerModel

interface DialogResponse {
    fun getResponse(boolean: Boolean, markerModel: MarkerModel)
}