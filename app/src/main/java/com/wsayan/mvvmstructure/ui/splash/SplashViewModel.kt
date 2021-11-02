package com.wsayan.mvvmstructure.ui.splash

import androidx.lifecycle.viewModelScope
import com.wsayan.mvvmstructure.network.NetworkState
import com.wsayan.mvvmstructure.network.data.Images
import com.wsayan.mvvmstructure.network.resolveError
import com.wsayan.mvvmstructure.repo.MoviesRepository
import com.wsayan.mvvmstructure.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    var moviesRepo: MoviesRepository
) : BaseViewModel() {
    fun getConfigurations() = flow {
        emit(NetworkState.Loading)

        try {
            emit(NetworkState.Data(moviesRepo.fetchConfigurations()))
        } catch (e: Exception) {
            emit(e.resolveError())
        }
    }

    fun saveConfigurations(images: Images) {
        viewModelScope.launch {
            moviesRepo.insertImageConfig(images)
        }
    }

    fun saveImageBaseUrl(url: String) = moviesRepo.cacheImageBaseUrl(url)

    val isLocalConfigExists = moviesRepo.isEmptyImageConfig()
}