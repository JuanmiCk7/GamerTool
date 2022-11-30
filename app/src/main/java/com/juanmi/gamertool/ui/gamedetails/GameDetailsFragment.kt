package com.juanmi.gamertool.ui.gamedetails


import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.GameDetailsFragmentBinding
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.model.getGenres
import com.juanmi.gamertool.model.getReleaseDate
import com.juanmi.gamertool.utils.adapters.ScreenshotsAdapter
import com.juanmi.gamertool.utils.formatCoverImageUrl
import com.juanmi.gamertool.utils.formatScreenshotBackgroundImageUrl
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameDetailsFragment : Fragment() {

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
        game = args.game
        comeFromMyGames()
        isFromFirestore()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setGameModel(args.game)
        setupView()
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

            try {
                Picasso.get()
                    .load(this.cover!!.url.formatCoverImageUrl())
                    .placeholder(R.drawable.gamertool_cover)
                    .error(R.drawable.gamertool_cover)
                    .into(binding.gameCover)
            } catch (e: Exception) {
                binding.gameCover.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_no_image_24))
            }

            try {
                Picasso.get()
                    .load(this.screenshots!!.first().url.formatScreenshotBackgroundImageUrl())
                    .placeholder(R.drawable.gamertool_cover)
                    .error(R.drawable.gamertool_cover)
                    .into(binding.headerBackground)
            } catch (e: Exception) {
                binding.gameCover.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_no_image_24))
            }

            binding.genresTextView.text = this.getGenres()
            binding.summary.text = this.summary
            binding.storyline.text = this.storyline

            binding.buttonToWishOrComplete.setOnClickListener {
                if(args.comeFromMyGames) {
                    viewModel.setState(game, !game.complete, requireContext())
                } else {
                    viewModel.saveGame(game, requireContext())
                }
            }

            binding.deleteButton.setOnClickListener {
                dialogDeleteGame()
            }
        }
    }

    private fun isFromFirestore() {
        if(!game.comesFromFirestore) {
            binding.deleteButton.visibility = View.INVISIBLE
        }
    }

    private fun comeFromMyGames() {
        if(args.comeFromMyGames) {
            if (!game.complete) {
                binding.buttonToWishOrComplete.text = resources.getText(R.string.finished_button)
            } else {
                binding.buttonToWishOrComplete.text = resources.getText(R.string.unfinished_button)
            }
        } else {
            binding.buttonToWishOrComplete.text = resources.getText(R.string.add_button)
        }
    }

    private fun dialogDeleteGame() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.alert_dialog_title)
        builder.setMessage(R.string.alert_dialog_text)

        builder.setPositiveButton(R.string.delete_button) { _, _ ->
            viewModel.deleteGame(game, requireContext(), requireView())
        }
        builder.setNegativeButton(R.string.cancel) { _, _ ->}
        builder.create()
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}