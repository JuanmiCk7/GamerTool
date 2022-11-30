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
import com.juanmi.gamertool.repository.auth.AuthRepository
import com.juanmi.gamertool.repository.firestore.FirestoreRepository
import com.juanmi.gamertool.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import com.juanmi.gamertool.utils.adapters.MyGamesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyGamesViewModel @Inject constructor(
    repository: FirestoreRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _games = MutableLiveData<ArrayList<Game>>()
    val games: LiveData<ArrayList<Game>> = _games

    @ExperimentalPagingApi
    val gameList: Flow<PagingData<Game>> =
        Pager(
            config = PagingConfig(
                pageSize = GameRepositoryImpl.GAME_PAGE_SIZE,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { MyGamesPagingSource(repository, authRepository) }
        ).flow

    fun onGameClicked(gameClicked: Game, view: View) {
        val action = MyGamesFragmentDirections
            .actionMyGamesFragmentToGameDetailsFragment(gameClicked, true)
        view.findNavController().navigate(action)
    }

}