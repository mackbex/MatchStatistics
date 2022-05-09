package com.match.statistics.util.ext

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import com.match.statistics.R


@BindingAdapter("spell_icons", "peak_icons")
fun bindSpellPeakIcons(tableLayout: TableLayout, spellIcons:List<String>?, peakIcons:List<String>?) {
    spellIcons?.forEachIndexed { index, spell ->
        val spellRow = tableLayout.getChildAt(index) as TableRow
        spellRow.findViewWithTag<ImageView>("SPELL").glide(spell)
    }

    peakIcons?.forEachIndexed { index, spell ->
        val spellRow = tableLayout.getChildAt(index) as TableRow
        spellRow.findViewWithTag<ImageView>("PEAK").glide(spell)
    }
}


@BindingAdapter("item_icons")
fun bindItemIcons(tableRow: TableRow, itemIcons:List<String>?) {

    itemIcons?.let { iconList ->
        tableRow.children.forEachIndexed { index, view ->
            iconList.getOrNull(index)?.let {
                (view as ImageView).glide(it)
            } ?: run {
                (view as ImageView).glide(ColorDrawable(ContextCompat.getColor(view.context, R.color.pale_grey_two)))
            }
        }
    }
}
