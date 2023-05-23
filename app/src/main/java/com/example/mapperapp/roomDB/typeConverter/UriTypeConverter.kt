package com.example.mapperapp.roomDB.typeConverter

import android.net.Uri
import androidx.room.TypeConverter

class UriTypeConverter {

    @TypeConverter
    fun uriFromString(value: String): Uri {
        return Uri.parse(value)
    }

    @TypeConverter
    fun uriToString(uri: Uri): String {
        return uri.toString()
    }

}