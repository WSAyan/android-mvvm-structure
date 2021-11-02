package com.wsayan.mvvmstructure.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wsayan.mvvmstructure.network.NetworkState
import com.wsayan.mvvmstructure.network.PostDataSource
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import com.wsayan.mvvmstructure.network.resolveError
import com.wsayan.mvvmstructure.repo.MoviesRepository
import com.wsayan.mvvmstructure.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    var moviesRepo: MoviesRepository
) : BaseViewModel() {
    fun getPopularMovies() = flow {
        emit(NetworkState.Loading)

        try {
            emit(NetworkState.Data(moviesRepo.fetchPopularMovies()))
        } catch (e: Exception) {
            emit(e.resolveError())
        }
    }

    fun getMovieDetails(id: Int) = flow {
        emit(NetworkState.Loading)

        try {
            emit(NetworkState.Data(moviesRepo.fetchMovieDetails(id)))
        } catch (e: Exception) {
            emit(e.resolveError())
        }
    }

    val getPopularMoviesList = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(moviesRepo)
    }.flow.cachedIn(viewModelScope)

    val imageBaseUrl = moviesRepo.imageBaseUrl

}