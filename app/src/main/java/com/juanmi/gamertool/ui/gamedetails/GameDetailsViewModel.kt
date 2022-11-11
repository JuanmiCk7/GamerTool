package com.juanmi.gamertool.ui.gamedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juanmi.gamertool.core.Event
import com.juanmi.gamertool.repository.model.Game

class GameDetailsViewModel : ViewModel() {

    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    private val _viewState = MutableLiveData<Event<Boolean>>()
    val viewState: LiveData<Event<Boolean>> = _viewState

    fun setGameModel(gameModel: Game) {
        _game.value = gameModel
        // TODO: define states to give ui responsiveness
        _viewState.value = Event(true)
    }

}