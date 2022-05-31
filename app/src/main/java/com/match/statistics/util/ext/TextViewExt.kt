package com.match.statistics.util.ext

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.match.statistics.R

/**
 * TextView Ext. 제플린에 기재된 대로 kda 사이 / 는 Bold가 아님.
 */
@BindingAdapter("match_kills","match_deaths","match_assists")
fun bindKda(textView: TextView, kills: Int = 0, deaths:Int = 0, assists:Int = 0) {
    textView.apply {
        val spannable = SpannableStringBuilder().apply {
            append("$kills")
            var start = length
            setSpan(StyleSpan(Typeface.BOLD),0,start, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            append(" / ")
            start = length
            append("$deaths")
            setSpan(StyleSpan(Typeface.BOLD),start,length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(ContextCompat.getColor(textView.context, R.color.darkish_pink)), start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            append(" / ")
            start = length
            append("$assists")
            setSpan(StyleSpan(Typeface.BOLD),start,length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        setText(spannable, TextView.BufferType.SPANNABLE)
    }
}