package com.match.statistics.util.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.match.statistics.domain.model.lol.League
import com.match.statistics.ui.custom.profile.LeagueAdapter


/**
 * Recyclerview databinding Ext
 */

@BindingAdapter("leagues")
fun bindLeagues(recyclerView: RecyclerView, leagues:  List<League>?) {
    val adapter = recyclerView.adapter as LeagueAdapter
    adapter.submitList(leagues)
}
