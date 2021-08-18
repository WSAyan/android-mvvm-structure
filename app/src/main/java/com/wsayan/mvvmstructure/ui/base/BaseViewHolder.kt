package com.wsayan.mvvmstructure.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wsayan.mvvmstructure.ui.common.IAdapterListener

abstract class BaseViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun<T> onBind(position: Int, model:T, mCallback : IAdapterListener)

}