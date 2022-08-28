package com.wsayan.mvvmstructure.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.databinding.FragmentSplashBinding
import com.wsayan.mvvmstructure.network.data.ConfigurationResponse
import com.wsayan.mvvmstructure.network.data.Images
import com.wsayan.mvvmstructure.ui.base.BaseFragment
import com.wsayan.mvvmstructure.ui.base.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()

    override fun viewRelatedTask() {

        lifecycleScope.launch {
            delay(3000)

            viewModel.isLocalConfigExists.collectLatest { isExists ->
                if (!isExists) {
                    viewModel.getConfigurations(R.id.config_state)
                } else {
                    gotoMovies()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.showProgressBar.collect {
                if (it) progressBarHandler.show() else progressBarHandler.hide()
            }
        }

        lifecycleScope.launch {
            viewModel.unauthorized.collect {
                if (it) forceLogout()
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                when (it) {
                    is UIState.Loading -> {

                    }
                    is UIState.DataLoaded -> {
                        cacheConfigData((it.data as ConfigurationResponse).images)

                        gotoMovies()
                    }
                    is UIState.Error -> {

                    }
                    else -> {

                    }
                }
            }
        }
    }


    private fun cacheConfigData(images: Images?) {
        if (images != null) {
            viewModel.saveConfigurations(images)

            images.base_url?.let { viewModel.saveImageBaseUrl(it) }
        }
    }

    private fun gotoMovies() = findNavController().navigate(R.id.moviesFragment)

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate
}