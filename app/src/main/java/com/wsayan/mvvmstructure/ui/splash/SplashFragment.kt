package com.wsayan.mvvmstructure.ui.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.databinding.FragmentSplashBinding
import com.wsayan.mvvmstructure.network.NetworkState
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
                    viewModel.getConfigurations().collectLatest {
                        when (it) {
                            is NetworkState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }
                            is NetworkState.Data -> {
                                binding.progressBar.visibility = View.GONE

                                it.data.images?.let { it1 -> viewModel.saveConfigurations(it1) }

                                findNavController().navigate(R.id.moviesFragment)
                            }
                            is NetworkState.Error -> {
                                binding.progressBar.visibility = View.GONE

                                requireContext().showToast(it.exception.message)
                            }
                        }
                    }
                } else {
                    findNavController().navigate(R.id.moviesFragment)
                }
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate
}