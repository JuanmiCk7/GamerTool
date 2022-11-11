package com.juanmi.gamertool.ui.paging

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.GameItemBinding
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.repository.model.getPlatformsNames
import com.juanmi.gamertool.repository.model.getReleaseDate
import com.juanmi.gamertool.utils.formatCoverImageUrl
import com.juanmi.gamertool.utils.setStarsProgressColor
import com.squareup.picasso.Picasso

class GameListPagingAdapter(
    private val itemClickFunction: (Game) -> Unit,
    private val onFinishRefresh: () -> Unit,
    private val primaryStarColor: Int,
    private val secondaryStarColor: Int,
    private val drawableFallbackImage: Drawable
) :
    PagingDataAdapter<Game, GameListPagingAdapter.GameViewHolder>(GameComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GameViewHolder(
            GameItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemClickFunction
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(
                it,
                primaryStarColor,
                secondaryStarColor,
                drawableFallbackImage
            )
        }
    }

    fun refreshList() {
        super.refresh()
        onFinishRefresh()
    }

    inner class GameViewHolder(
        private val itemBinding: GameItemBinding,
        private val clickFunction: (Game) -> Unit,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(
            game: Game,
            primaryStarColor: Int,
            secondaryStarColor: Int,
            drawableFallbackImage: Drawable
        ) {
            itemBinding.ratingBar.apply {
                val stars = itemBinding.ratingBar.progressDrawable as LayerDrawable
                stars.setStarsProgressColor(primaryStarColor, secondaryStarColor)
                progressDrawable = stars
            }

            try {
                Picasso.get()
                    .load(game.cover?.url.formatCoverImageUrl())
                    .placeholder(R.drawable.igdb_cover)
                    .error(R.drawable.igdb_cover)
                    .into(itemBinding.gameCover)
            } catch (e: Exception) {
                itemBinding.gameCover.setImageDrawable(drawableFallbackImage)
            }

            game.name?.let {
                itemBinding.title.text = game.name
            } ?: run {
                itemBinding.title.text = DEFAULT_TITLE_STRING
            }

            game.releaseDate?.let {
                itemBinding.date.text = game.getReleaseDate()
            } ?: run {
                itemBinding.date.visibility = View.INVISIBLE
            }

            game.platforms?.let {
                val platformString = game.getPlatformsNames()
                if (platformString.isNullOrEmpty()) {
                    itemBinding.platform.visibility = View.INVISIBLE
                } else {
                    itemBinding.platform.text = platformString
                }
            } ?: run {
                itemBinding.platform.visibility = View.INVISIBLE
            }

            game.rating?.let {
                itemBinding.ratingBar.rating = (game.rating / 20).toFloat()
            } ?: run {
                itemBinding.ratingBar.rating = 0.0f
            }

            this.itemView.setOnClickListener {
                clickFunction(game)
            }
        }
    }

    object GameComparator : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Game, newItem: Game) =
            oldItem.id == newItem.id && oldItem.name == newItem.name
    }

    companion object {
        const val DEFAULT_TITLE_STRING = "Unknown game "
    }
}