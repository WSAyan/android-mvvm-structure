package com.wsayan.mvvmstructure.ui.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.databinding.FragmentMoviesBinding
import com.wsayan.mvvmstructure.databinding.ItemMovieBinding
import com.wsayan.mvvmstructure.network.data.ResultsItem
import com.wsayan.mvvmstructure.ui.base.BaseFragment
import com.wsayan.mvvmstructure.ui.base.BaseRecyclerAdapter
import com.wsayan.mvvmstructure.ui.base.BaseViewHolder
import com.wsayan.mvvmstructure.ui.common.IAdapterListener
import com.wsayan.mvvmstructure.ui.common.PagerLoadStateAdapter
import com.wsayan.mvvmstructure.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieListAdapter: MovieListAdapter

    override fun viewRelatedTask() {
        binding.toolbar.backIV.visibility = View.GONE
        binding.toolbar.titleTV.text = getString(R.string.movies)

        handleMovieRecycler()

        lifecycleScope.launch {
            viewModel.getPopularMoviesList.collectLatest { pagingData ->
                movieListAdapter.submitData(pagingData)
            }
        }
    }

    private fun handleMovieRecycler() {
        movieListAdapter = MovieListAdapter(onItemClick = { data, position, view ->
            when (view.id) {
                R.id.itemLayout -> {
                    findNavController().navigate(
                        R.id.action_movies_to_details, bundleOf(
                            getString(R.string.movie_bundle_key) to data
                        )
                    )
                }
            }
        })

        movieListAdapter.imageBaseUrl = viewModel.imageBaseUrl

        binding.movieList.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieListAdapter.withLoadStateFooter(
                footer = PagerLoadStateAdapter { movieListAdapter.retry() }
            )
        }

        movieListAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                progressBarHandler.show()
            } else {
                // Hide ProgressBar
                progressBarHandler.hide()

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    requireContext().showToast(it.error.message)
                }
            }
        }
    }

    private fun initRecycler(results: List<ResultsItem?>?) {
        binding.movieList.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        binding.movieList.adapter =
            BaseRecyclerAdapter(requireContext(), object : IAdapterListener {

                override fun <T> clickListener(position: Int, model: T, view: View) {
                    when (view.id) {
                        R.id.itemLayout -> {
                            findNavController().navigate(R.id.action_movies_to_details)
                        }
                    }
                }

                override fun getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
                    return MovieListViewHolder(
                        ItemMovieBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        ), requireContext()
                    )
                }
            }, results as MutableList<ResultsItem?>)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesBinding
        get() = FragmentMoviesBinding::inflate
}