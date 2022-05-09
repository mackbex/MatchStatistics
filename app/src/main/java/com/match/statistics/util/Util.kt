package com.match.statistics.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import java.text.SimpleDateFormat
import java.util.*

fun getProgressbar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
}

fun modifyToValidUrl(url:String):String {

    val index = url.indexOf("//")
    return if(index > -1) {
        "https://" + url.substring(index+2)
    }
    else {
        url
    }
}

fun convertTimestampToDateString(timestamp:Long, format:String):String {
    val sdf = SimpleDateFormat(format)
    val date = sdf.format(timestamp)

    return date.toString()
}
