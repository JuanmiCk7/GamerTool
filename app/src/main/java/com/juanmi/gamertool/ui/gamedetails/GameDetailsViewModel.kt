package com.juanmi.gamertool.ui.gamedetails

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.juanmi.gamertool.core.Event
import com.juanmi.gamertool.repository.auth.AuthRepository
import com.juanmi.gamertool.repository.firestore.FirestoreRepository
import com.juanmi.gamertool.repository.model.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(firestoreRepository: FirestoreRepository, private val authRepository: AuthRepository) : ViewModel() {

    private val _game = MutableLiveData<Game>()
    val game: LiveData<Game> = _game

    private val _viewState = MutableLiveData<Event<Boolean>>()
    val viewState: LiveData<Event<Boolean>> = _viewState

    val repository = firestoreRepository

    fun setGameModel(gameModel: Game) {
        _game.value = gameModel
        // TODO: define states to give ui responsiveness
        _viewState.value = Event(true)
    }

    fun saveGame(game: Game, context: Context) {
        repository.saveGame(game, context, authRepository.currentUser!!)
    }

    fun setCompleted(game: Game, context: Context) {
        repository.setCompleted(game, context, authRepository.currentUser!!)
    }

    fun deleteGame(game: Game, context: Context, view: View) {
        repository.deleteGame(game, context, authRepository.currentUser!!)
        val action = GameDetailsFragmentDirections.actionGameDetailsFragmentToMyGamesFragment()
        view.findNavController().navigate(action)
    }

}