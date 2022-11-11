package com.juanmi.gamertool.repository.retrofit

import com.juanmi.gamertool.core.network.ResultData
import com.juanmi.gamertool.repository.model.Game

/**
 * Interfaz que define los métodos con los que haremos las consultas a la API.
 */
interface GameRepository {
    suspend fun getGames(currentPage: Int): ResultData<ArrayList<Game>?>

    suspend fun getGamesByName(name: String): ResultData<ArrayList<Game>?>
}