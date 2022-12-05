package com.juanmi.gamertool.ui.searchgames

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juanmi.gamertool.R
import com.juanmi.gamertool.databinding.FoundGamesFragmentBinding
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.pagging.adapters.LoadStateAdapter
import com.juanmi.gamertool.pagging.adapters.GameListPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchGamesFragment : Fragment() {

    private var _binding: FoundGamesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var gamesAdapter: GameListPagingAdapter

    private val defaultGameName = "Mario"

    private val viewModel: SearchGamesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoundGamesFragmentBinding.inflate(inflater, container, false)
        viewModel.setGameName(defaultGameName)
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

        binding.gameSearchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = gamesAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { gamesAdapter.retry() }
            )
        }

        val searchIcon : ImageView = binding.searchView.findViewById(androidx.appcompat.R.id.search_button)
        val cleanIcon : ImageView = binding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        searchIcon.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.search_view_icon))
        cleanIcon.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.search_view_clean_icon))

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    viewModel.setGameName(query)
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query != null) {
                    viewModel.setGameName(query)
                }
                return true
            }
        })

        gamesAdapter.addLoadStateListener { loadState ->
            if((loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) && gamesAdapter.itemCount == 0) {
                binding.progressBar.visibility = View.VISIBLE
            }
            else if(loadState.refresh is LoadState.NotLoading || loadState.append is LoadState.NotLoading) {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        viewModel.gameList.observe(viewLifecycleOwner) {
            gamesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun Fragment.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
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