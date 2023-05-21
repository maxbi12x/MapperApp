package com.example.mapperapp

import com.example.mapperapp.models.MarkerModel

interface DailogResponse {
    fun getResponse(boolean: Boolean, markerModel: MarkerModel)
}