package com.wsayan.mvvmstructure.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wsayan.mvvmstructure.data.MovieListResponse
import com.wsayan.mvvmstructure.repo.MoviesRepository
import com.wsayan.mvvmstructure.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    var moviesRepo: MoviesRepository
) : BaseViewModel() {

    var movieListResponse = MutableLiveData<MovieListResponse>()

    // with live data
    fun apiResponseInit() {
        viewModelScope.launch {
            onLoading(true)

            try {
                val response = moviesRepo.apiResponses()
                if (response.code() == 401) forceLogOut(true)

                val baseModel = convertData(MovieListResponse::class, response) as MovieListResponse
                movieListResponse.value = baseModel
            } catch (exception: Exception) {

            }

            onLoading(false)
        }

    }


    // with unit callback
    fun apiResponseWithCallBacks(
        result: (data: MovieListResponse?, success: Boolean, message: String?, shouldLogout: Boolean) -> Unit,
        loading: (isLoading: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            loading(true)
            var data: MovieListResponse? = null
            var success = false
            var message: String? = null
            var shouldLogout = false

            try {
                val response = moviesRepo.apiResponses()
                success = response.isSuccessful
                shouldLogout = response.code() == 401
                message = response.message()

                data = convertData(MovieListResponse::class, response) as MovieListResponse
            } catch (exception: Exception) {
                message = exception.message
            }

            result(data, success, message, shouldLogout)
            loading(false)
        }
    }
}