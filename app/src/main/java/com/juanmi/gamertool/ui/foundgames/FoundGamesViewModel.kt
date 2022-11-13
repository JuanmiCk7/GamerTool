package com.juanmi.gamertool.ui.foundgames

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.paging.*
import com.juanmi.gamertool.core.Event
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import com.juanmi.gamertool.ui.paging.GameSearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FoundGamesViewModel @Inject constructor(repository: GameRepository) : ViewModel() {

    private val _games = MutableLiveData<ArrayList<Game>>()
    val games: LiveData<ArrayList<Game>> = _games

    private val _gameName = MutableLiveData<String>()
    val gameName: LiveData<String> = _gameName

    @ExperimentalPagingApi
    val gameList: Flow<PagingData<Game>> =
        Pager(
            config = PagingConfig(
                pageSize = GameRepositoryImpl.GAME_FETCH_NUMBER,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { GameSearchPagingSource(repository, gameName.value.orEmpty()) }
        ).flow

    fun onGameClicked(gameClicked: Game, view: View) {
        val action = FoundGamesFragmentDirections
            .actionFoundGamesFragmentToGameDetailsFragment(gameClicked, false)
        view.findNavController().navigate(action)
    }

    fun setGameName(gameName: String) {
        _gameName.value = gameName
    }
}


