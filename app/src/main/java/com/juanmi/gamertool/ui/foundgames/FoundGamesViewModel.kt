package com.juanmi.gamertool.ui.foundgames

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.paging.*
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import com.juanmi.gamertool.repository.retrofit.PagingGameRepository
import com.juanmi.gamertool.utils.adapters.GameSearchPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retry
import javax.inject.Inject

@HiltViewModel
class FoundGamesViewModel @Inject constructor(private val repository: PagingGameRepository) : ViewModel() {

    private val _games = MutableLiveData<ArrayList<Game>>()
    val games: LiveData<ArrayList<Game>> = _games

    private val _gameName = MutableLiveData<String>()
    private val gameName: LiveData<String> = _gameName

    @ExperimentalPagingApi
    var gameList = gameName.switchMap { name ->
        repository.getSearchResults(name).cachedIn(viewModelScope)
    }

    fun onGameClicked(gameClicked: Game, view: View) {
        val action = FoundGamesFragmentDirections
            .actionFoundGamesFragmentToGameDetailsFragment(gameClicked, false)
        view.findNavController().navigate(action)
    }

    fun setGameName(gameName: String) {
        _gameName.value = gameName
    }
}


