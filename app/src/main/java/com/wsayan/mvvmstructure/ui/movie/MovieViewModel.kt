package com.wsayan.mvvmstructure.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsayan.mvvmstructure.network.DataResult
import com.wsayan.mvvmstructure.network.data.MovieListResponse
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

    // with live data
    fun apiResponseInit() {
        /*viewModelScope.launch {
            onLoading(true)

            try {
                val response = moviesRepo.fetchPopularMovies()
                if (response.code() == 401) forceLogOut(true)

                val baseModel = convertData(MovieListResponse::class, response) as MovieListResponse
                movieListResponse.value = baseModel
            } catch (exception: Exception) {

            }

            onLoading(false)
        }*/


    }

    fun getPopularMovies() = flow {
        emit(DataResult.loading(message = "loading"))
        try {
            emit(
                DataResult.success(
                    data = moviesRepo.fetchPopularMovies()
                )
            )
        } catch (e: Exception) {
            emit(
                DataResult.error(
                    error = e,
                    message = "Something went wrong"
                )
            )
        }
    }

}