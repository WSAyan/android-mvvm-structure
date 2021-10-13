package com.wsayan.mvvmstructure.ui.splash

import com.wsayan.mvvmstructure.repo.MoviesRepository
import com.wsayan.mvvmstructure.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    var moviesRepo: MoviesRepository
) : BaseViewModel() {
}