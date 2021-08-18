package com.wsayan.mvvmstructure.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.data.ResultsItem
import com.wsayan.mvvmstructure.databinding.FragmentMoviesBinding
import com.wsayan.mvvmstructure.databinding.ItemMovieBinding
import com.wsayan.mvvmstructure.ui.base.BaseFragment
import com.wsayan.mvvmstructure.ui.base.BaseRecyclerAdapter
import com.wsayan.mvvmstructure.ui.base.BaseViewHolder
import com.wsayan.mvvmstructure.ui.common.IAdapterListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MoviesFragment : BaseFragment<FragmentMoviesBinding>() {
    private val viewModel: MovieViewModel by viewModels()

    override fun viewRelatedTask() {
        binding.toolbar.backIV.visibility = View.GONE
        binding.toolbar.titleTV.text = getString(R.string.movies)

        showMovieList()
    }

    private fun movieListObservers() {
        viewModel.apiResponseInit()

        viewModel.movieListResponse.observe(viewLifecycleOwner) {
            initRecycler(it.results)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { showLoader ->
            if (showLoader) {
                progressBarHandler.show()
            } else {
                progressBarHandler.hide()
            }
        }


        viewModel.forceLogOut.observe(viewLifecycleOwner) { logOut ->
            if (logOut) {
                //findNavController().navigate(R.id.action_startFragment_to_firstFragment)
            }
        }
    }

    // using unit call back
    private fun showMovieList() {
        viewModel.apiResponseWithCallBacks(
            { data, success, message, shouldLogout ->
                if (shouldLogout) {
                    //findNavController().navigate(R.id.action_startFragment_to_firstFragment)
                    return@apiResponseWithCallBacks
                }

                if (success) {
                    initRecycler(data?.results)
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            },
            {
                if (it) {
                    progressBarHandler.show()
                } else {
                    progressBarHandler.hide()
                }
            }
        )
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