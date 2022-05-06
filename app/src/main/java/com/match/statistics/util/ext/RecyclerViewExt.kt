package com.match.statistics.util.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.match.statistics.R
import com.match.statistics.domain.model.League
import com.match.statistics.util.Resource
import com.match.statistics.domain.model.SearchResult
import com.match.statistics.domain.model.Summoner
import com.match.statistics.domain.model.Theme
import com.match.statistics.ui.company.horizontal_theme.HorizontalThemeAdapter
import com.match.statistics.ui.company.search.SearchResultAdapter
import com.match.statistics.ui.statistics.profile.ProfileAdapter


/**
 * Recyclerview databinding Ext
 */

@BindingAdapter("items")
fun bindItems(recyclerView: RecyclerView, items: Resource<SearchResult>?) {
    val adapter = recyclerView.adapter as SearchResultAdapter
    when(items) {
        is Resource.Success -> {
            adapter.submitList(items.data.items)
        }
        is Resource.Failure -> {
            Snackbar.make(recyclerView.rootView, recyclerView.context.getString(R.string.err_failed_load_data), Snackbar.LENGTH_SHORT).show()
        }
    }
}

@BindingAdapter("leagues")
fun bindLeagues(recyclerView: RecyclerView, summoner:  Resource<Summoner>?) {
    val adapter = recyclerView.adapter as ProfileAdapter
    when(summoner) {
        is Resource.Success -> {
            adapter.submitList(summoner.data.leagues)
        }
        is Resource.Failure -> {
            Snackbar.make(recyclerView.rootView, recyclerView.context.getString(R.string.err_failed_load_data), Snackbar.LENGTH_SHORT).show()
        }
    }
}

@BindingAdapter("theme_items")
fun bindThemeItems(recyclerView: RecyclerView, items: List<Theme>?) {
    val adapter = recyclerView.adapter as HorizontalThemeAdapter
    adapter.submitList(items)
}