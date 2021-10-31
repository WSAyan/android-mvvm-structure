package com.wsayan.mvvmstructure.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsayan.mvvmstructure.network.DataResult
import com.wsayan.mvvmstructure.network.NetworkState
import com.wsayan.mvvmstructure.network.data.MovieListResponse
import com.wsayan.mvvmstructure.network.resolveData
import com.wsayan.mvvmstructure.network.resolveError
import com.wsayan.mvvmstructure.repo.MoviesRepository
import com.wsayan.mvvmstructure.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    var moviesRepo: MoviesRepository
) : BaseViewModel() {
    var movieListResponse = MutableLiveData<MovieListResponse>()

    fun getPopularMovies() = flow {
        emit(NetworkState.Loading)
        try {
            emit(NetworkState.Data(moviesRepo.fetchPopularMovies()))
        } catch (e: Exception) {
            emit(e.resolveError())
        }
    }

}