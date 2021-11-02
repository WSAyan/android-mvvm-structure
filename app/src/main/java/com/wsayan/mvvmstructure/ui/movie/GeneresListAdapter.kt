package com.wsayan.mvvmstructure.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wsayan.mvvmstructure.databinding.ItemGeneresBinding
import com.wsayan.mvvmstructure.network.data.Genres

class GeneresListAdapter(
    val onItemClick: (
        data: Genres?,
        position: Int,
        view: View
    ) -> Unit
) : RecyclerView.Adapter<GeneresListAdapter.ViewHolder>() {
    var items = mutableListOf<Genres>()

    inner class ViewHolder(val binding: ItemGeneresBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genres, position: Int) {
            binding.nameTV.text = item.name
            binding.itemLayout.setOnClickListener {
                onItemClick(item, position, binding.itemLayout)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGeneresBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addAll(generes: MutableList<Genres>) {
        items.addAll(generes)
        notifyDataSetChanged()
    }
}