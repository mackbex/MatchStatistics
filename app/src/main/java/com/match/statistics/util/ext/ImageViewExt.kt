package com.match.statistics.util.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
/**
 * ImageView databinding Ext
 */


@BindingAdapter("view_icon")
fun bindViewIcon(imageView: ImageView, imgUrl: String?) {
    imageView.glide(imgUrl)
}



