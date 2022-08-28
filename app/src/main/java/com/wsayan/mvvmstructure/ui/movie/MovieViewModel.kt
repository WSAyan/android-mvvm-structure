package com.wsayan.mvvmstructure.ui.movie

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.wsayan.mvvmstructure.network.PostDataSource
import com.wsayan.mvvmstructure.repo.IMoviesRepository
import com.wsayan.mvvmstructure.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    var moviesRepo: IMoviesRepository
) : BaseViewModel() {

    fun getMovieDetails(stateId: Int, id: Int) {
        viewModelScope.launch {
            moviesRepo.fetchMovieDetails(id).collect {
                generateUiState(stateId, it)
            }
        }
    }

    val getPopularMoviesList = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(moviesRepo)
    }.flow.cachedIn(viewModelScope)

    val imageBaseUrl = moviesRepo.imageBaseUrl()

}