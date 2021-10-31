package com.wsayan.mvvmstructure.ui.movie

import androidx.lifecycle.MutableLiveData
import com.wsayan.mvvmstructure.network.NetworkState
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

}