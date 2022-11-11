package com.juanmi.gamertool.ui.gamedetails

import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.GameDetailsFragmentBinding
import com.juanmi.gamertool.repository.model.getPlatformsNames
import com.juanmi.gamertool.repository.model.getReleaseDate
import com.juanmi.gamertool.ui.gamedetails.adapters.ScreenshotsAdapter
import com.juanmi.gamertool.utils.formatCoverImageUrl
import com.juanmi.gamertool.utils.formatScreenshotBackgroundImageUrl
import com.juanmi.gamertool.utils.setStarsProgressColor
import com.squareup.picasso.Picasso

class GameDetailsFragment : Fragment() {

    private var _binding: GameDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameDetailsViewModel by viewModels()

    private lateinit var screenshotsAdapter: ScreenshotsAdapter

    private val args: GameDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defineObservables()
        viewModel.setGameModel(args.game)
    }

    private fun defineObservables() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            setupView()
        }
    }

    private fun setupView() {
        with(viewModel.game.value!!) {
            screenshotsAdapter = ScreenshotsAdapter(
                this.screenshots.orEmpty(),
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_no_image_24)!!
            )
            binding.screenshotsRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = screenshotsAdapter
            }

            binding.title.text = this.name
            binding.date.text = this.getReleaseDate()

            val stars = binding.ratingBar.progressDrawable as LayerDrawable
            stars.setStarsProgressColor(
                resources.getColor(R.color.GamerToolsoftViolet),
                resources.getColor(R.color.GamerToolsoftGray)
            )
            binding.ratingBar.progressDrawable = stars

            try {
                Picasso.get()
                    .load(this.cover!!.url.formatCoverImageUrl())
                    .placeholder(R.drawable.igdb_cover)
                    .error(R.drawable.igdb_cover)
                    .into(binding.gameCover)
            } catch (e: Exception) {
                binding.gameCover.setImageDrawable(resources.getDrawable(R.drawable.ic_no_image_24))
            }

            try {
                Picasso.get()
                    .load(this.screenshots!!.first().url.formatScreenshotBackgroundImageUrl())
                    .placeholder(R.drawable.igdb_app_background)
                    .error(R.drawable.igdb_app_background)
                    .into(binding.headerBackground)
            } catch (e: Exception) {
                binding.headerBackground.setImageDrawable(resources.getDrawable(R.drawable.ic_no_image_24))
            }

            binding.platforms.text = this.getPlatformsNames()

            binding.summary.text = this.summary
            binding.storyline.text = this.storyline

            binding.linkToWeb.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this.url))
                startActivity(browserIntent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}