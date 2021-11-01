package com.wsayan.mvvmstructure.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.databinding.FragmentMovieDetailsBinding
import com.wsayan.mvvmstructure.network.NetworkState
import com.wsayan.mvvmstructure.network.data.ResultsItem
import com.wsayan.mvvmstructure.ui.base.BaseFragment
import com.wsayan.mvvmstructure.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    private val viewModel: MovieViewModel by viewModels()

    override fun viewRelatedTask() {
        binding.toolbar.titleTV.text = getString(R.string.now_what)

        binding.toolbar.backIV.setOnClickListener {
            findNavController().popBackStack()
        }

        val movieId = arguments?.getParcelable<ResultsItem>(getString(R.string.movie_bundle_key))?.id ?: return

        lifecycleScope.launch {
            viewModel.getMovieDetails(movieId).collect {
                when (it) {
                    is NetworkState.Loading -> {
                        progressBarHandler.show()
                    }
                    is NetworkState.Data -> {
                        progressBarHandler.hide()

                    }
                    is NetworkState.Error -> {
                        progressBarHandler.hide()

                        if (it.unauthorized) {
                            // todo: handle unauthorized action
                            forceLogout()
                        }
                        it.exception.printStackTrace()
                        requireContext().showToast(it.exception.message)
                    }
                }
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate
}