package com.juanmi.gamertool.ui.mygames

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.MyGamesFragmentBinding
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.pagging.adapters.LoadStateAdapter
import com.juanmi.gamertool.pagging.adapters.GameListPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyGamesFragment : Fragment() {

    private var _binding: MyGamesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var gamesAdapter: GameListPagingAdapter

    private val viewModel: MyGamesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyGamesFragmentBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun setupView() {

        gamesAdapter = GameListPagingAdapter(
            this::onGameClicked,
            this::finishRefreshing,
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_no_image_24)!!
        )

        binding.swipeContainer.setOnRefreshListener {
            gamesAdapter.refreshList()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.gameList.collectLatest { pagingData ->
                gamesAdapter.submitData(pagingData)
            }
        }

        gamesAdapter.addLoadStateListener { loadState ->
            if((loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) && gamesAdapter.itemCount == 0) {
                binding.progressBar.visibility = View.VISIBLE
            }
            else if(loadState.refresh is LoadState.NotLoading || loadState.append is LoadState.NotLoading) {
                binding.progressBar.visibility = View.INVISIBLE
            }

            noGamesTextViewVisibility()
        }

        binding.mygamesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = gamesAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { gamesAdapter.retry() }
            )
        }
    }

    private fun noGamesTextViewVisibility() {
        if(gamesAdapter.itemCount == 0) {
            binding.noGamesTv.visibility = View.VISIBLE
        } else {
            binding.noGamesTv.visibility = View.INVISIBLE
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