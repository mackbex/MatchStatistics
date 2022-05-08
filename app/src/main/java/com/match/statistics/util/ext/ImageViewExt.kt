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


@BindingAdapter("profileImageUrl")
fun bindProfileImageUrl(imageView: ImageView, imgUrl: String?) {
    val circularProgressDrawable = getProgressbar(imageView.context)
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(imgUrl)
        .fitCenter()
        .error(ColorDrawable(ContextCompat.getColor(imageView.context,R.color.pale_grey)))
        .placeholder(circularProgressDrawable)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                circularProgressDrawable.stop()
                return false
            }
        })
        .into(imageView)
}



