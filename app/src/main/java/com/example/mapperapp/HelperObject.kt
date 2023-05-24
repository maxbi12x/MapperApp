package com.example.mapperapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.format.DateUtils
import java.io.File
import java.util.*

object HelperObject {
    fun getRelativeTime(date: Date): String {
        val currentTime = Date()
        val relativeTime = DateUtils.getRelativeTimeSpanString(date.time, currentTime.time, DateUtils.MINUTE_IN_MILLIS)
        return relativeTime.toString()
    }
    fun getBitmap(uri : String) : Bitmap?{
        val imgFile = File(uri)
        var r : Bitmap? = null
        if (imgFile.exists()) {
            r = BitmapFactory.decodeFile(imgFile.absolutePath)

        }
        return r
    }
}