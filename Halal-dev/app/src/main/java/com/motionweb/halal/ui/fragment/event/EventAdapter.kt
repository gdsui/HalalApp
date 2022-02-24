package com.motionweb.halal.ui.fragment.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.motionweb.halal.R
import com.motionweb.halal.data.network.banner.Banner
import com.motionweb.halal.data.network.event.Event
import com.motionweb.halal.databinding.ItemEventBinding

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private var dataSource: List<Banner> = listOf()

    fun submitList(newList: List<Banner>) {
        val result = DiffUtil.calculateDiff(EventDiffUtil(dataSource, newList))
        dataSource = newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSource[position])
    }

    override fun getItemCount() = dataSource.size


    class ViewHolder(private val view: ItemEventBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(event: Banner) {
            //view.tvTitle.text = event.title
            val rightUrl = event.banner.replace("web:8001", "159.65.120.217")
            view.ivEventBanner.load(rightUrl) { crossfade(true) }
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val view = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(view)
            }
        }
    }
}

class EventDiffUtil(private val newList: List<Banner>, private val oldList: List<Banner>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}