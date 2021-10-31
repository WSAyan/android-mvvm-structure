package com.wsayan.mvvmstructure.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wsayan.mvvmstructure.databinding.ItemLoadStateBinding

class PagerLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PagerLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        val binding = holder.loadStateViewBinding

        binding.retryBTN.isVisible = loadState !is LoadState.Loading
        binding.errorMessageTV.isVisible = loadState !is LoadState.Loading
        binding.loadProgress.isVisible = loadState is LoadState.Loading

        if (loadState is LoadState.Error) {
            binding.errorMessageTV.text = loadState.error.localizedMessage
        }

        binding.retryBTN.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class LoadStateViewHolder(val loadStateViewBinding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(loadStateViewBinding.root)
}