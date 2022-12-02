package com.juanmi.gamertool.utils.adapters

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.RecyclerViewGameBinding
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.model.getGenres
import com.juanmi.gamertool.model.getReleaseDate
import com.juanmi.gamertool.utils.formatCoverImageUrl
import com.juanmi.gamertool.utils.setStarsProgressColor
import com.squareup.picasso.Picasso

class GameListPagingAdapter(
    private val itemClickFunction: (Game) -> Unit,
    private val onFinishRefresh: () -> Unit,
    private val errorImage: Drawable
) :
    PagingDataAdapter<Game, GameListPagingAdapter.GameViewHolder>(GameComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GameViewHolder(
            RecyclerViewGameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            itemClickFunction
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, errorImage)
        }
    }

    fun refreshList() {
        super.refresh()
        onFinishRefresh()
    }

    inner class GameViewHolder(
        private val itemBinding: RecyclerViewGameBinding,
        private val clickFunction: (Game) -> Unit,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(game: Game, errorImage: Drawable) {

            itemBinding.ratingBar.apply {
                val stars = itemBinding.ratingBar.progressDrawable as LayerDrawable
                stars.setStarsProgressColor(
                    ContextCompat.getColor(context, R.color.blue),
                    ContextCompat.getColor(context, R.color.medium_gray))
                progressDrawable = stars
            }

            try {
                Picasso.get()
                    .load(game.cover?.url.formatCoverImageUrl())
                    .placeholder(R.drawable.gamertool_cover)
                    .error(R.drawable.gamertool_cover)
                    .into(itemBinding.gameCover)
            } catch (e: Exception) {
                itemBinding.gameCover.setImageDrawable(errorImage)
            }

            game.name?.let {
                itemBinding.title.text = game.name
            } ?: run {
                itemBinding.title.text = "Unknown"
            }

            game.genres?.let {
                itemBinding.genres.text = game.getGenres()
            }

            if(game.complete) {
                itemBinding.completeGameImageView.visibility = View.VISIBLE
            } else {
                itemBinding.completeGameImageView.visibility = View.INVISIBLE
            }

            game.releaseDate?.let {
                itemBinding.date.text = game.getReleaseDate()
            } ?: run {
                itemBinding.date.visibility = View.INVISIBLE
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
}