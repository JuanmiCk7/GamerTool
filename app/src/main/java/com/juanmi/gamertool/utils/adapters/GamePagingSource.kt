package com.juanmi.gamertool.utils.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.juanmi.gamertool.application.api.ResultData
import com.juanmi.gamertool.repository.model.Game
import com.juanmi.gamertool.repository.retrofit.GameRepository
import com.juanmi.gamertool.repository.retrofit.GameRepositoryImpl
import retrofit2.HttpException

class GamesPagingSource(private val repository: GameRepository) :
    PagingSource<Int, Game>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        val pageNumber = params.key ?: 1
        return try {
            val response = repository.getGames(pageNumber)
            var resultData: ArrayList<Game>? = null
            var nextPageNumber: Int? = null
            if (response is ResultData.Success &&
                response.data!!.size == GameRepositoryImpl.GAME_PAGE_SIZE
            ) {
                nextPageNumber = pageNumber + 1
                resultData = response.data
            }

            LoadResult.Page(
                data = resultData!!.toList(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
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