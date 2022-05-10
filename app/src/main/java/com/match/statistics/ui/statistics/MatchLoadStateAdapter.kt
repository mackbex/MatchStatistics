package com.match.statistics.ui.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.match.statistics.R
import com.match.statistics.databinding.LayoutMatchLoadStateBinding

class MatchLoadStateAdapter(
    private val retry: (() -> Unit)?
): LoadStateAdapter<MatchLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_match_load_state,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }
    class ViewHolder(private val binding: LayoutMatchLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState:LoadState, retryListener: (() -> Unit)?) {
            binding.btnRetry.isVisible = loadState is LoadState.Error
            binding.tvStateError.isVisible = loadState is LoadState.Error
            binding.progress.isVisible = loadState is LoadState.Loading

            binding.btnRetry.setOnClickListener {
                retryListener?.invoke()
            }
        }
    }
}