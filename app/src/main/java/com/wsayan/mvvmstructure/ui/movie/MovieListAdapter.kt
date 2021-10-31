package com.wsayan.mvvmstructure.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.wsayan.mvvmstructure.databinding.ItemMovieBinding
import com.wsayan.mvvmstructure.network.data.ResultsItem
import com.wsayan.mvvmstructure.ui.common.IAdapterListener

class MovieListAdapter(val onItemClick: (data: ResultsItem?, position: Int, view: View) -> Unit) :
    PagingDataAdapter<ResultsItem, MovieListAdapter.ViewHolder>(DataDifferentiator) {

    inner class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsItem, position: Int) {


            binding.nameTV.text = item.originalTitle
            binding.dateTV.text = item.releaseDate
            binding.itemLayout.setOnClickListener {
                onItemClick(item, position, binding.itemLayout)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item, position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    object DataDifferentiator : DiffUtil.ItemCallback<ResultsItem>() {

        override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
            return oldItem == newItem
        }
    }

}