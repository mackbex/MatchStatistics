package com.match.statistics.ui.custom.analysis

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.match.statistics.R
import com.match.statistics.databinding.LayoutAnalysisBinding
import com.match.statistics.domain.model.lol.Champion
import com.match.statistics.ui.statistics.StatisticsViewModel
import com.match.statistics.util.getLifeCycleOwner
import com.match.statistics.util.getProgressbar

/**
 * 최근 게임 분석 뷰
 */
class LayoutAnalysis : ConstraintLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    var binding: LayoutAnalysisBinding = LayoutAnalysisBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        getLifeCycleOwner(this)?.let {
            binding.lifecycleOwner = it
        }
    }

    var games:Int?
        get() =
            binding.games
        set(value) {
            binding.games = value
        }

    var wins:Int?
        get() = binding.wins
        set(value) {
            binding.wins = value
        }

    var losses:Int?
        get() = binding.losses
        set(value) {
            binding.losses = value
        }

    var kills:Float?
        get() = binding.kills
        set(value) {
            binding.kills = value
        }

    var deaths:Float?
        get() = binding.deaths
        set(value) {
            binding.deaths = value
        }

    var assists:Float?
        get() = binding.assists
        set(value) {
            binding.assists = value
        }

    var kdaRatio:Float?
        get() = binding.kdaRatio
        set(value) {
            binding.kdaRatio = value
        }

    var killContrib:Int?
        get() = binding.killContrib
        set(value) {
            binding.killContrib = value
        }

    var mostChampions:List<Champion>?
        get() = binding.mostChampions
        set(value) {
            binding.mostChampions = value
        }

    var mostPosition:String?
        get() = binding.mostPosition
        set(value) {
            binding.mostPosition = value
        }

    var positionWinRate:Int?
        get() = binding.positionWinRate
        set(value) {
            binding.positionWinRate = value
        }

    fun setViewModel(viewModel: StatisticsViewModel) {
        binding.viewModel = viewModel
    }

    companion object {
        @BindingAdapter("kda_ratio")
        @JvmStatic
        fun bindKdaRatio(textView: TextView, ratio: Float = 0f) {
            textView.apply {
                textView.text = "${String.format("%.2f", ratio)}:1"
                when {
                    ratio > 4 -> { setTextColor(ContextCompat.getColor(context, R.color.azure)) }
                    ratio > 2 -> { setTextColor(ContextCompat.getColor(context, R.color.green_kda)) }
                    else -> { setTextColor(ContextCompat.getColor(context, R.color.gunmetal)) }
                }
            }
        }

        @BindingAdapter("kill_contribution")
        @JvmStatic
        fun bindKillContrib(textView: TextView, contrib: Int = 0) {
           textView.apply {
                textView.text = "($contrib%)"
                if (contrib > 50) {
                    setTextColor(ContextCompat.getColor(context, R.color.darkish_pink))
                }
                else {
                    setTextColor(ContextCompat.getColor(context, R.color.gunmetal))
                }
            }
        }

        @BindingAdapter("analysis_kills","analysis_deaths","analysis_assists")
        @JvmStatic
        fun bindKda(textView: TextView, kills: Float = 0f, deaths:Float = 0f, assists:Float = 0f) {
            textView.apply {
                val spannable = SpannableStringBuilder().apply {
                    append(String.format("%.1f", kills))
                    var start = length
                    setSpan(StyleSpan(Typeface.BOLD),0,start,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    append(" / ")
                    start = length
                    append(String.format("%.1f", deaths))
                    setSpan(StyleSpan(Typeface.BOLD),start,length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(ForegroundColorSpan(ContextCompat.getColor(textView.context, R.color.darkish_pink)), start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    append(" / ")
                    start = length
                    append(String.format("%.1f", assists))
                    setSpan(StyleSpan(Typeface.BOLD),start,length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }

                setText(spannable, TextView.BufferType.SPANNABLE)
            }
        }

        @BindingAdapter("most_champion")
        @JvmStatic
        fun bindMostChampions(imageView: ImageView, championUrl:String?) {
            setImage(imageView, championUrl)
        }

        @BindingAdapter("most_position")
        @JvmStatic
        fun bindMostPosition(imageView: ImageView, position:String?) {
            position?.let {
                val positionIcon = when(it.uppercase()) {
                    "TOP" -> ContextCompat.getDrawable(imageView.context,R.drawable.ic_lol_top)
                    "JNG" -> ContextCompat.getDrawable(imageView.context,R.drawable.ic_lol_jng)
                    "MID" -> ContextCompat.getDrawable(imageView.context,R.drawable.ic_lol_mid)
                    "ADC" -> ContextCompat.getDrawable(imageView.context,R.drawable.ic_lol_bot)
                    "SUP" -> ContextCompat.getDrawable(imageView.context,R.drawable.ic_lol_sup)
                    else -> null
                }

                setImage(imageView, positionIcon)
            }

        }

        fun setImage(imageView: ImageView, item:Any?) {
            val circularProgressDrawable = getProgressbar(imageView.context)
            circularProgressDrawable.start()

            Glide.with(imageView.context)
                .load(item)
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
