package com.juanmi.gamertool.utils.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.juanmi.gamertool.repository.auth.AuthRepository
import com.juanmi.gamertool.repository.firestore.FirestoreRepository
import com.juanmi.gamertool.model.Game

class MyGamesPagingSource(private val repository: FirestoreRepository, private val authRepository: AuthRepository)
    : PagingSource<Int, Game>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {

        return try {
            LoadResult.Page(
                data = repository.getAllGames(authRepository.currentUser!!),
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