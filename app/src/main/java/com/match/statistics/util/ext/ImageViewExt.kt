package com.match.statistics.util.ext

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.match.statistics.R
import com.match.statistics.util.getProgressbar

/**
 * ImageView databinding Ext
 */


@BindingAdapter("view_icon")
fun bindViewIcon(imageView: ImageView, imgUrl: String?) {
    imageView.glide(imgUrl)
}



