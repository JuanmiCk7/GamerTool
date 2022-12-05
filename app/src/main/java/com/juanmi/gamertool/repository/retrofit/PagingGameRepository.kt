package com.juanmi.gamertool.repository.retrofit

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.juanmi.gamertool.pagging.sources.GameSearchPagingSource
import com.juanmi.gamertool.pagging.sources.GamesPagingSource
import javax.inject.Inject

class PagingGameRepository @Inject constructor(private val repository: GameRepository) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = GameRepositoryImpl.GAME_PAGE_SIZE,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { GameSearchPagingSource(repository, query) }
        ).liveData

    fun getAllGames() =
        Pager(
            config = PagingConfig(
                pageSize = GameRepositoryImpl.GAME_PAGE_SIZE,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { GamesPagingSource(repository) }
        ).liveData

}