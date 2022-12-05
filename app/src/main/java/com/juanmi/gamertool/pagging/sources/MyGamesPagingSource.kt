package com.juanmi.gamertool.pagging.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.QuerySnapshot
import com.juanmi.gamertool.repository.auth.AuthRepository
import com.juanmi.gamertool.repository.firestore.FirestoreRepository
import com.juanmi.gamertool.model.Game
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException

private const val PAGE_SIZE = 50

class MyGamesPagingSource(private val repository: FirestoreRepository, private val authRepository: AuthRepository)
    : PagingSource<QuerySnapshot, Game>() {

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Game> {

        return try {

            val currentPage = params.key ?: repository.getAllGames(authRepository.currentUser!!).get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
            var nextPage = repository.getAllGames(authRepository.currentUser!!).startAfter(lastVisibleProduct).get().await()

            if(currentPage.size() != PAGE_SIZE) {
                nextPage = null
            }

            LoadResult.Page(
                data = currentPage.toObjects(Game::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Game>): QuerySnapshot? {
        return null
    }
}