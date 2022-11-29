package com.juanmi.gamertool.utils.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.GameScreenshotBinding
import com.juanmi.gamertool.repository.model.GameScreenshot
import com.juanmi.gamertool.utils.formatScreenshotImageUrl
import com.squareup.picasso.Picasso

class ScreenshotsAdapter(
    private val screenshots: List<GameScreenshot>,
    private val drawableFallbackImage: Drawable
) : RecyclerView.Adapter<ScreenshotsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            screenshots[position],
            drawableFallbackImage
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val item =
            GameScreenshotBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return screenshots.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(
        private val itemBinding: GameScreenshotBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            screenshot: GameScreenshot,
            drawableFallbackImage: Drawable
        ) {
            try {
                Picasso.get()
                    .load(screenshot.url.formatScreenshotImageUrl())
                    .placeholder(R.drawable.gamertool_cover)
                    .error(R.drawable.gamertool_cover)
                    .into(itemBinding.screenshotImage)
            } catch (e: Exception) {
                itemBinding.screenshotImage.setImageDrawable(drawableFallbackImage)
            }
        }
    }
}