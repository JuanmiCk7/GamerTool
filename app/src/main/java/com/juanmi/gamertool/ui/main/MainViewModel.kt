package com.juanmi.gamertool.ui.main

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.paging.*
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.repository.retrofit.PagingGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: PagingGameRepository,
) : ViewModel() {

    @ExperimentalPagingApi
    var gameList = repository.getAllGames().cachedIn(viewModelScope)

    fun onGameClicked(gameClicked: Game, view: View) {
        val action = MainFragmentDirections
            .actionMainFragmentToGameDetailsFragment(gameClicked, false)
        view.findNavController().navigate(action)
    }
}