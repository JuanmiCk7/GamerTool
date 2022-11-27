package com.juanmi.gamertool.ui.foundgames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.FoundGamesFragmentBinding
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.ui.paging.LoadStateAdapter
import com.juanmi.gamertool.ui.paging.GameListPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FoundGamesFragment : Fragment() {

    private var _binding: FoundGamesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagingAdapter: GameListPagingAdapter

    private val viewModel: FoundGamesViewModel by viewModels()

    private val args: FoundGamesFragmentArgs by navArgs()

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoundGamesFragmentBinding.inflate(inflater, container, false)
        viewModel.setGameName(args.gameName)
        setupView()
        return binding.root
    }

    @ExperimentalPagingApi
    private fun setupView() {

        binding.swipeContainer.setOnRefreshListener {
            pagingAdapter.refreshList()
        }

        binding.swipeContainer.setColorSchemeResources(
            R.color.GamerToolstrongBlue,
            R.color.GamerToolstrongBlue,
            R.color.GamerToolstrongBlue,
            R.color.GamerToolstrongBlue,
        )

        pagingAdapter = GameListPagingAdapter(
            { game -> onGameClicked(game) },
            { finishRefreshing() },
            ContextCompat.getColor(requireContext(), R.color.GamerToolsoftBlue),
            ContextCompat.getColor(requireContext(), R.color.GamerToolsoftGray),
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_no_image_24)!!
        )

        binding.gameSearchRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = pagingAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { pagingAdapter.retry() }
            )

        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.gameList.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun onGameClicked(game: Game) {
        viewModel.onGameClicked(game, requireView())
    }

    private fun finishRefreshing() {
        binding.swipeContainer.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}