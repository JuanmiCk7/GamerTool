package com.juanmi.gamertool.ui.foundgames

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.paging.*
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.repository.retrofit.PagingGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoundGamesViewModel @Inject constructor(private val repository: PagingGameRepository) : ViewModel() {

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


