package com.match.statistics.util

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.match.statistics.R
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

fun getLifeCycleOwner(view: View): LifecycleOwner? {
    var context = view.context

    while (context is ContextWrapper) {
        if (context is LifecycleOwner) {
            return context
        }
        context = context.baseContext
    }

    return null
}


fun dateConvert(time:Long, context:Context): String {
    return when (val diff = (Date().time / 1000 - time) / 1000) {
        in 0 until 10 -> context.getString(R.string.date_unit_recent)
        in 10 until 60 -> "${diff}${context.getString(R.string.date_unit_second)}"
        in 60 until 60 * 60 -> "${diff / 60}${context.getString(R.string.date_unit_minute)}"
        in 60 * 60 until 60 * 60 * 24 -> "${diff / (60 * 60)}${context.getString(R.string.date_unit_hour)}"
        in 60 * 60 * 24 until 60 * 60 * 48 -> context.getString(R.string.date_unit_yesterday)
        in 60 * 60 * 48 until 60 * 60 * 24 * 7 -> "${diff / (60 * 60 * 24)}${context.getString(R.string.date_unit_day)}"
        else -> SimpleDateFormat("MM.dd", Locale.getDefault()).format(time)
    }
}
