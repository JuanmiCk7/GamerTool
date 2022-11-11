package com.juanmi.gamertool.ui.paging

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class FooterLoaderAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterLoaderViewHolder>() {
    override fun onBindViewHolder(holder: FooterLoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterLoaderViewHolder {
        return FooterLoaderViewHolder.create(parent, retry)
    }
}