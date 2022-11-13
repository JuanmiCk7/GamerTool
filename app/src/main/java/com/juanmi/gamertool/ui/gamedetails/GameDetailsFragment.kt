package com.juanmi.gamertool.ui.gamedetails


import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.GameDetailsFragmentBinding
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.repository.model.getPlatformsNames
import com.juanmi.gamertool.repository.model.getReleaseDate
import com.juanmi.gamertool.ui.gamedetails.adapters.ScreenshotsAdapter
import com.juanmi.gamertool.utils.formatCoverImageUrl
import com.juanmi.gamertool.utils.formatScreenshotBackgroundImageUrl
import com.juanmi.gamertool.utils.setStarsProgressColor
import com.squareup.picasso.Picasso

class GameDetailsFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    private var _binding: GameDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var game: Game

    private val viewModel: GameDetailsViewModel by viewModels()

    private lateinit var screenshotsAdapter: ScreenshotsAdapter

    private val args: GameDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameDetailsFragmentBinding.inflate(inflater, container, false)
        if(args.comeFromMyGames) {
            binding.buttonToWishOrComplete.text = resources.getText(R.string.finished_button)
        } else {
            binding.buttonToWishOrComplete.text = resources.getText(R.string.add_button)
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defineObservables()
        game = args.game
        viewModel.setGameModel(args.game)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun defineObservables() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            setupView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

            binding.buttonToWishOrComplete.setOnClickListener {
                if(args.comeFromMyGames) {
                    setCompleted()
                } else {
                    saveGame()
                }
            }
        }
    }

    private fun saveGame() {
        val data = hashMapOf(
            "id" to game.id,
            "cover" to game.cover,
            "genres" to game.genres,
            "name" to game.name,
            "platforms" to game.platforms,
            "storyline" to game.storyline,
            "summary" to game.summary,
            "url" to game.url,
            "releaseDate" to game.releaseDate,
            "rating" to game.rating,
            "totalRating" to game.rating,
            "ratingCount" to game.ratingCount,
            "screenshots" to game.screenshots,
            "complete" to game.complete
        )


        db.collection("games").document(game.id.toString()).set(data)
            .addOnSuccessListener {
                Toast.makeText(context, "Game saved into wish list!", Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(
                    context,
                    "Failed saving the game into wish list!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun setCompleted() {
        db.collection("games").document(game.id.toString()).update("complete", true)
            .addOnSuccessListener {
                Toast.makeText(context, "Game setted as complete!", Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(
                    context,
                    "Failed to save the game state to complete!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}