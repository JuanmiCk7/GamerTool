package com.juanmi.gamertool.repository.retrofit

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.juanmi.gamertool.model.Game

/**
 * Interfaz que define los métodos con los que creamos el objeto Pager en base a una petición.
 */
interface PagingGameRepository {
    fun getSearchResults(query: String) : LiveData<PagingData<Game>>
    fun getAllGames() : LiveData<PagingData<Game>>
}