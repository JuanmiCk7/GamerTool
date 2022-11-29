package com.juanmi.gamertool.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.paging.*
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import com.juanmi.gamertool.utils.adapters.GamesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: GameRepository,
) : ViewModel() {

    private val _games = MutableLiveData<ArrayList<Game>>()
    val games: LiveData<ArrayList<Game>> = _games

    @ExperimentalPagingApi
    val gameList: Flow<PagingData<Game>> =
        Pager(
            config = PagingConfig(
                pageSize = GameRepositoryImpl.GAME_PAGE_SIZE,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { GamesPagingSource(repository) }
        ).flow

    fun onGameClicked(gameClicked: Game, view: View) {
        val action = MainFragmentDirections
            .actionMainFragmentToGameDetailsFragment(gameClicked, false)
        view.findNavController().navigate(action)
    }
}