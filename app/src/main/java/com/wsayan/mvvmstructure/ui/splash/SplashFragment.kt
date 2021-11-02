package com.wsayan.mvvmstructure.ui.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.databinding.FragmentSplashBinding
import com.wsayan.mvvmstructure.db.entity.ImagesConfig
import com.wsayan.mvvmstructure.network.NetworkState
import com.wsayan.mvvmstructure.network.data.Images
import com.wsayan.mvvmstructure.ui.base.BaseFragment
import com.wsayan.mvvmstructure.ui.movie.MovieViewModel
import com.wsayan.mvvmstructure.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
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
                    downLoadData()
                } else {
                    gotoMovies()
                }
            }
        }
    }

    private suspend fun downLoadData() {
        viewModel.getConfigurations().collectLatest {
            when (it) {
                is NetworkState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is NetworkState.Data -> {
                    binding.progressBar.visibility = View.GONE

                    cacheConfigData(it.data.images)

                    gotoMovies()
                }
                is NetworkState.Error -> {
                    binding.progressBar.visibility = View.GONE

                    requireContext().showToast(it.exception.message)
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