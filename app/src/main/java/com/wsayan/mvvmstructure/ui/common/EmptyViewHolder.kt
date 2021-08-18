package com.wsayan.mvvmstructure.ui.common

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.wsayan.mvvmstructure.databinding.ItemEmptyBinding
import com.wsayan.mvvmstructure.ui.base.BaseViewHolder

class EmptyViewHolder (itemView: ViewDataBinding, context: Context) :
    BaseViewHolder(itemView.root) {
    var binding = itemView as ItemEmptyBinding

    override fun <T> onBind(position: Int, itemModel: T, listener: IAdapterListener) {

    }
}