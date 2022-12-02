package com.juanmi.gamertool.ui.main

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.paging.*
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import com.juanmi.gamertool.repository.retrofit.PagingGameRepository
import com.juanmi.gamertool.utils.adapters.GamesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: PagingGameRepository,
) : ViewModel() {

    private val _games = MutableLiveData<ArrayList<Game>>()
    val games: LiveData<ArrayList<Game>> = _games

    @ExperimentalPagingApi
    var gameList = repository.getAllGames().cachedIn(viewModelScope)

    fun onGameClicked(gameClicked: Game, view: View) {
        val action = MainFragmentDirections
            .actionMainFragmentToGameDetailsFragment(gameClicked, false)
        view.findNavController().navigate(action)
    }
}