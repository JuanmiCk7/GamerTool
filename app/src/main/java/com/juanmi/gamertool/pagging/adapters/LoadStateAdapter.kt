package com.juanmi.gamertool.pagging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.LoadStateItemBinding

/***
 * Adaptador para el manejo de errores al cargar la lista de juegos.
 */
class LoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }
}

/***
 * ViewHolder del manejador de errores.
 */
class LoadStateViewHolder(
    private val binding: LoadStateItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_item, parent, false)
            val binding = LoadStateItemBinding.bind(view)
            return LoadStateViewHolder(binding, retry)
        }
    }
}