package com.match.statistics.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.match.statistics.R
import com.match.statistics.databinding.ItemLeaguesBinding
import com.match.statistics.databinding.ItemMatchBinding
import com.match.statistics.domain.model.lol.League
import com.match.statistics.domain.model.lol.Match

class MatchHistoryAdapter : PagingDataAdapter<Match, MatchHistoryAdapter.ViewHolder>(ItemDiffCallback()) {

    private var listener: ((match: Match, binding:ViewDataBinding) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_match,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       getItem(position)?.let { holder.bind(it) }
    }

    fun setPostInterface(listener: ((match:Match, binding:ViewDataBinding) -> Unit)?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match) {
            binding.setVariable(BR.match, match)
            listener?.invoke(match, binding)
            binding.executePendingBindings()
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem.gameId == newItem.gameId && oldItem.createDate == newItem.createDate
        }
    }
}