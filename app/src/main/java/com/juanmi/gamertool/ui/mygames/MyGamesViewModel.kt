package com.juanmi.gamertool.ui.mygames

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.juanmi.gamertool.core.Event
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import com.juanmi.gamertool.ui.foundgames.FoundGamesFragmentDirections
import com.juanmi.gamertool.ui.paging.GameSearchPagingSource
import com.juanmi.gamertool.ui.paging.MyGamesPagingSource
import kotlinx.coroutines.flow.Flow

class MyGamesViewModel : ViewModel() {

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
            pagingSourceFactory = { MyGamesPagingSource() }
        ).flow

    fun onGameClicked(gameClicked: Game, view: View) {
        val action = MyGamesFragmentDirections
            .actionMyGamesFragmentToGameDetailsFragment(gameClicked, true)
        view.findNavController().navigate(action)
    }

    fun setGameName(gameName: String) {
        _gameName.value = gameName
    }

}