package com.juanmi.gamertool.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.MainFragmentBinding
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.ui.paging.FooterLoaderAdapter
import com.juanmi.gamertool.ui.paging.GameListPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pagingAdapter: GameListPagingAdapter

    private val viewModel: MainViewModel by viewModels()

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        setupView()

        return binding.root
    }

    @ExperimentalPagingApi
    private fun setupView() {

        binding.swipeContainer.setOnRefreshListener {
            pagingAdapter.refreshList()
        }

        binding.swipeContainer.setColorSchemeResources(
            R.color.GamerToolstrongViolet,
            R.color.GamerToolstrongViolet,
            R.color.GamerToolstrongViolet,
            R.color.GamerToolstrongViolet,
        )

        pagingAdapter = GameListPagingAdapter(
            { game -> onGameClicked(game) },
            { finishRefreshing() },
            resources.getColor(R.color.GamerToolsoftViolet),
            resources.getColor(R.color.GamerToolsoftGray),
            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_no_image_24)!!
        )
        binding.gameListRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = pagingAdapter.withLoadStateFooter(
                footer = FooterLoaderAdapter { pagingAdapter.retry() }
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