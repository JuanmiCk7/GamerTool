package com.juanmi.gamertool.repository.retrofit

import com.juanmi.gamertool.application.api.ResultData
import com.juanmi.gamertool.model.Game

/**
 * Interfaz que define los métodos con los que haremos las consultas a la API.
 */
interface GameRepository {
    suspend fun getGames(currentPage: Int): ResultData<ArrayList<Game>?>
    suspend fun getGamesByName(name: String, currentPage: Int): ResultData<ArrayList<Game>?>
}