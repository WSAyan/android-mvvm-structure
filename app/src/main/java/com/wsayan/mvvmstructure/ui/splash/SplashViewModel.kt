package com.wsayan.mvvmstructure.ui.splash

import androidx.lifecycle.viewModelScope
import com.wsayan.mvvmstructure.network.data.Images
import com.wsayan.mvvmstructure.repo.IMoviesRepository
import com.wsayan.mvvmstructure.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    var moviesRepo: IMoviesRepository
) : BaseViewModel() {

    fun getConfigurations(stateId: Int) {
        viewModelScope.launch {
            moviesRepo.fetchConfigurations().collect {
                generateUiState(stateId, it)
            }
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