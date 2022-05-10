package com.match.statistics.ui.custom.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.match.statistics.R
import com.match.statistics.databinding.ItemLeaguesBinding
import com.match.statistics.domain.model.lol.League

/**
 * 리그 어뎁터
 */
class LeagueAdapter : ListAdapter<League, LeagueAdapter.ViewHolder>(ItemDiffCallback()) {

    private var listener: ((league: League, binding:ViewDataBinding) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_leagues,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setPostInterface(listener: ((league:League, binding:ViewDataBinding) -> Unit)?) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: ItemLeaguesBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(league: League) {
                binding.setVariable(BR.league, league)
                listener?.invoke(league, binding)
                binding.executePendingBindings()
            }
        }

        private class ItemDiffCallback : DiffUtil.ItemCallback<League>() {
            override fun areItemsTheSame(oldItem: League, newItem: League): Boolean {
                return oldItem.wins == newItem.losses && oldItem.losses == newItem.losses
            }

            override fun areContentsTheSame(oldItem: League, newItem: League): Boolean {
                return oldItem == newItem
            }
        }
    }