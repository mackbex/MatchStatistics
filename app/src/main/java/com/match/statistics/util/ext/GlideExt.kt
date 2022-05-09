package com.match.statistics.util.ext

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.match.statistics.R
import com.match.statistics.util.getProgressbar


fun ImageView.glide(drawable: Drawable, endAction:(() -> Unit)? = null) {

    val holder = getProgressbar(this.context).also {
        it.start()
    }

    Glide.with(this.context)
        .load(drawable)
        .fitCenter()
        .error(ColorDrawable(ContextCompat.getColor(this.context, R.color.pale_grey)))
        .placeholder(holder)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.stop()
                endAction?.invoke()
                return false
            }
        })
        .into(this)
}

fun ImageView.glide(url:String?, endAction:(() -> Unit)? = null) {

    val holder = getProgressbar(this.context).also {
        it.start()
    }

    Glide.with(this.context)
        .load(url)
        .fitCenter()
        .error(ColorDrawable(ContextCompat.getColor(this.context, R.color.pale_grey)))
        .placeholder(holder)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.stop()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.stop()
                endAction?.invoke()
                return false
            }
        })
        .into(this)
}