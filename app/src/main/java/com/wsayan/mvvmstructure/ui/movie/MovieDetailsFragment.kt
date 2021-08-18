package com.wsayan.mvvmstructure.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wsayan.mvvmstructure.R
import com.wsayan.mvvmstructure.databinding.FragmentMovieDetailsBinding
import com.wsayan.mvvmstructure.ui.base.BaseFragment

class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    private val viewModel: MovieViewModel by viewModels()

    override fun viewRelatedTask() {
        binding.toolbar.titleTV.text = getString(R.string.now_what)

        binding.toolbar.backIV.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate
}