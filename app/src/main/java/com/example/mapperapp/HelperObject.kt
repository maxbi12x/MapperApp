package com.example.mapperapp

import android.text.format.DateUtils
import java.util.*

object HelperObject {
    fun getRelativeTime(date: Date): String {
        val currentTime = Date()
        val relativeTime = DateUtils.getRelativeTimeSpanString(date.time, currentTime.time, DateUtils.MINUTE_IN_MILLIS)
        return relativeTime.toString()
    }
}