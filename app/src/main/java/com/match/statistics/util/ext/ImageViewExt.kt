package com.match.statistics.util.ext

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.match.statistics.R
import com.match.statistics.domain.model.Theme
import com.match.statistics.util.getProgressbar

/**
 * ImageView databinding Ext
 */

@BindingAdapter("coverImage")
fun bindCoverImage(imageView: ImageView, model: Theme?) {
    val circularProgressDrawable = CircularProgressDrawable(imageView.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(model?.cover_image)
        .fitCenter()
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .skipMemoryCache(true)
        .error(ColorDrawable(Color.parseColor(model?.color)))
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

@BindingAdapter("companyLogo")
fun bindCompanyLogo(imageView: ImageView, logoPath:String?) {

    val circularProgressDrawable = CircularProgressDrawable(imageView.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
    }
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(logoPath)
        .fitCenter()
//        .diskCacheStrategy(DiskCacheStrategy.NONE)
//        .skipMemoryCache(true)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.logo_failed)
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

@BindingAdapter("tierImage")
fun bindTierImage(imageView: ImageView, tierImage:String?) {
    val circularProgressDrawable = getProgressbar(imageView.context)
    circularProgressDrawable.start()

    Glide.with(imageView.context)
        .load(tierImage)
        .fitCenter()
        .placeholder(circularProgressDrawable)
        .error(R.drawable.logo_failed)
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