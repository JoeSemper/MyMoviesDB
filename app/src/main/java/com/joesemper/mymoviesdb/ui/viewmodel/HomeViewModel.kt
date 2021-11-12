package com.joesemper.mymoviesdb.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.joesemper.mymoviesdb.data.model.Result
import com.joesemper.mymoviesdb.data.repository.MoviesRepository
import com.joesemper.mymoviesdb.utils.sortByPairs

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {

    val pager = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) { getAllPopularMovies() }.flow.cachedIn(viewModelScope)

    suspend fun getPopularMovies(page: Int) = repository.getPopularMovies(page)

    fun getAllPopularMovies(): PagingSource<Int, List<Result>> {
        return object : PagingSource<Int, List<Result>>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, List<Result>> {

                val pageNumber = params.key ?: 0

                val response = getPopularMovies(pageNumber + 1)
                val result = sortByPairs(response.results)
                val prevKey = if (pageNumber > 0) pageNumber - 1 else null
                val nextKey = if (response.results.isNotEmpty()) pageNumber + 1 else null

                return LoadResult.Page(
                    data = result,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }

            override fun getRefreshKey(state: PagingState<Int, List<Result>>): Int? {
                return state.anchorPosition?.let {
                    state.closestPageToPosition(it)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
                }
            }
        }
    }

}

