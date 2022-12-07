package com.juanmi.gamertool.repository.retrofit.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.juanmi.gamertool.pagging.sources.GameSearchPagingSource
import com.juanmi.gamertool.pagging.sources.GamesPagingSource
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.PagingGameRepository
import javax.inject.Inject

/**
 * Clase que implementa los métodos utilizados para crear un objeto Pager en base a una petición en el repositorio de juegos.
 */
class PagingGameRepositoryImpl @Inject constructor(private val repository: GameRepository) : PagingGameRepository {

    /**
     * Método que devuelve un objeto Pager en base a una búsqueda por nombre.
     */
    override fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = GameRepositoryImpl.GAME_PAGE_SIZE,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { GameSearchPagingSource(repository, query) }
        ).liveData

    /**
     * Método que devuelve un objeto Pager en base a una búsqueda genérica.
     */
    override fun getAllGames() =
        Pager(
            config = PagingConfig(
                pageSize = GameRepositoryImpl.GAME_PAGE_SIZE,
                prefetchDistance = 2
            ),
            pagingSourceFactory = { GamesPagingSource(repository) }
        ).liveData

}