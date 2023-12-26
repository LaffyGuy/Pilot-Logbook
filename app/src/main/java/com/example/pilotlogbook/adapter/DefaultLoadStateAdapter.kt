package com.example.pilotlogbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pilotlogbook.databinding.PartResultBinding

class DefaultLoadStateAdapter: LoadStateAdapter<DefaultLoadStateAdapter.DefaultLoadStateHolder>() {
    class DefaultLoadStateHolder(
        private val binding: PartResultBinding,
        private val swipeRefreshLayout: SwipeRefreshLayout?,
        ): RecyclerView.ViewHolder(binding.root) {


        fun bind(loadState: LoadState){
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.tvError.isVisible = loadState is LoadState.Error
            binding.btnTryAgain.isVisible = loadState is LoadState.Error
            if(swipeRefreshLayout != null){
                swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
                binding.progressBar.isVisible = false
            }else{
                binding.progressBar.isVisible = loadState is LoadState.Loading
            }
        }

    }

    override fun onBindViewHolder(holder: DefaultLoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): DefaultLoadStateHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PartResultBinding.inflate(inflater, parent, false)
        return DefaultLoadStateHolder(binding, null)
    }
}