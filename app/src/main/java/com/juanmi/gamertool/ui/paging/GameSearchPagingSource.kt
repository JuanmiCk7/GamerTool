package com.juanmi.gamertool.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.juanmi.gamertool.core.network.ResultData
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepository

class GameSearchPagingSource(private val repository: GameRepository, private val gameName: String) :
    PagingSource<Int, Game>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        return try {
            val response = repository.getGamesByName(gameName)
            var resultData: ArrayList<Game>? = null
            if (response is ResultData.Success) {
                resultData = response.data
            }

            LoadResult.Page(
                data = resultData!!.toList(),
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}