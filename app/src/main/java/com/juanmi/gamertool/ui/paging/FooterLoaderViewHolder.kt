package com.juanmi.gamertool.ui.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.LoadingFooterBinding

class FooterLoaderViewHolder(
    private val binding: LoadingFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): FooterLoaderViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_footer, parent, false)
            val binding = LoadingFooterBinding.bind(view)
            return FooterLoaderViewHolder(binding, retry)
        }
    }
}