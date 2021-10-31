package com.wsayan.mvvmstructure.ui.movie

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.wsayan.mvvmstructure.network.data.ResultsItem
import com.wsayan.mvvmstructure.databinding.ItemMovieBinding
import com.wsayan.mvvmstructure.ui.base.BaseViewHolder
import com.wsayan.mvvmstructure.ui.common.IAdapterListener

class MovieListViewHolder(
    itemView: ViewBinding,
    context: Context
) : BaseViewHolder(itemView.root) {

    var binding = itemView as ItemMovieBinding
    var mContext: Context = context

    override fun <T> onBind(position: Int, itemModel: T, listener: IAdapterListener) {
        itemModel as ResultsItem

        binding.nameTV.text = itemModel.title
        binding.dateTV.text = itemModel.releaseDate

        binding.itemLayout.setOnClickListener {
            listener.clickListener(position, itemModel, binding.itemLayout)
        }
    }
}