package com.wsayan.mvvmstructure.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.network.data.ResultsItem
import com.wsayan.mvvmstructure.databinding.FragmentMoviesBinding
import com.wsayan.mvvmstructure.databinding.ItemMovieBinding
import com.wsayan.mvvmstructure.network.DataResult
import com.wsayan.mvvmstructure.network.NetworkState
import com.wsayan.mvvmstructure.ui.base.BaseFragment
import com.wsayan.mvvmstructure.ui.base.BaseRecyclerAdapter
import com.wsayan.mvvmstructure.ui.base.BaseViewHolder
import com.wsayan.mvvmstructure.ui.common.IAdapterListener
import com.wsayan.mvvmstructure.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {
    private val viewModel: MovieViewModel by viewModels()

    override fun viewRelatedTask() {
        binding.toolbar.backIV.visibility = View.GONE
        binding.toolbar.titleTV.text = getString(R.string.movies)

        lifecycleScope.launch {
            viewModel.getPopularMovies().collect {
                when (it) {
                    is NetworkState.Loading -> {
                        progressBarHandler.show()
                    }
                    is NetworkState.Data -> {
                        progressBarHandler.hide()
                        initRecycler(it.data.results)

                    }
                    is NetworkState.Error -> {
                        progressBarHandler.hide()
                        Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        //movieListObservers()
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
            }, results as ArrayList<ResultsItem?>)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesBinding
        get() = FragmentMoviesBinding::inflate
}