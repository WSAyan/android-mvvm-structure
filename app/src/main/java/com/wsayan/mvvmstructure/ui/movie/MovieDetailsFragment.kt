package com.wsayan.mvvmstructure.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.databinding.FragmentMovieDetailsBinding
import com.wsayan.mvvmstructure.network.NetworkState
import com.wsayan.mvvmstructure.network.data.MovieDetailsResponse
import com.wsayan.mvvmstructure.network.data.ResultsItem
import com.wsayan.mvvmstructure.ui.base.BaseFragment
import com.wsayan.mvvmstructure.ui.common.PagerLoadStateAdapter
import com.wsayan.mvvmstructure.util.loadNetworkImage
import com.wsayan.mvvmstructure.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var generesListAdapter: GeneresListAdapter

    override fun viewRelatedTask() {
        binding.toolbar.titleTV.text = getString(R.string.now_what)

        binding.toolbar.backIV.setOnClickListener {
            findNavController().popBackStack()
        }

        handleGeneresRecycler()

        val movieId =
            arguments?.getParcelable<ResultsItem>(getString(R.string.movie_bundle_key))?.id
                ?: return



        lifecycleScope.launch {
            viewModel.getMovieDetails(movieId).collect {
                when (it) {
                    is NetworkState.Loading -> {
                        progressBarHandler.show()
                    }
                    is NetworkState.Data -> {
                        progressBarHandler.hide()

                        showData(it.data)
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

    private fun handleGeneresRecycler() {
        generesListAdapter = GeneresListAdapter(
            onItemClick = { data, position, view ->
                when (view.id) {
                    R.id.itemLayout -> {
                        findNavController().navigate(
                            R.id.action_movies_to_details, bundleOf(
                                getString(R.string.movie_bundle_key) to data
                            )
                        )
                    }
                }
            }
        )

        binding.generesRV.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = generesListAdapter
        }
    }

    private fun showData(data: MovieDetailsResponse) {
        binding.posterIV.loadNetworkImage(
            requireContext(),
            "${viewModel.imageBaseUrl}original${data.backdrop_path}"
        )

        binding.nameTV.text = data.original_title

        data.genres?.toMutableList()?.let { generesListAdapter.addAll(it) }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate
}