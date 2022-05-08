package com.match.statistics.ui.custom.profile

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.match.statistics.R
import com.match.statistics.databinding.LayoutProfileBinding
import com.match.statistics.domain.model.lol.League
import com.match.statistics.util.getProgressbar

class LayoutProfile : ConstraintLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    var binding: LayoutProfileBinding = LayoutProfileBinding.inflate(LayoutInflater.from(context), this, true)

    var profileImageUrl:String?
        get() = binding.profileImageUrl
        set(value) {
            binding.profileImageUrl = value
        }

    var level:Int?
        get() = binding.level
        set(value) {
            binding.level = value
        }

    var name:String?
        get() = binding.name
        set(value) {
            binding.name = value
        }

    var leagues:List<League>?
        get() = binding.leagues
        set(value) {
            binding.leagues = value
        }

    companion object {

        @BindingAdapter("leagues")
        @JvmStatic
        fun bindLeagues(recyclerView: RecyclerView, leagues:  List<League>?) {
            val adapter = recyclerView.adapter as ProfileAdapter
            adapter.submitList(leagues)
        }

        @BindingAdapter("profileImageUrl")
        @JvmStatic
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

        @BindingAdapter("tierImage")
        @JvmStatic
        fun bindTierImage(imageView: ImageView, tierImage:String?) {
            val circularProgressDrawable = getProgressbar(imageView.context)
            circularProgressDrawable.start()

            Glide.with(imageView.context)
                .load(tierImage)
                .fitCenter()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.bg_circle_pale)
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
    }
}
