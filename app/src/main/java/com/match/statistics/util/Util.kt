package com.match.statistics.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun getProgressbar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
}