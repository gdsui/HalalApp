package com.motionweb.halal.ui.fragment.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.motionweb.halal.data.network.banner.Banner
import com.motionweb.halal.databinding.ItemBannerBinding

class HalalBannerAdapter : RecyclerView.Adapter<HalalBannerAdapter.HalalViewHolder>() {

    private var bannerList: List<Banner> = emptyList()

    fun submitItems(items: List<Banner>) {
        bannerList = items
        // TODO("add DiffUtil if there are a lot of frequently changed data")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HalalViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HalalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HalalViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size

    class HalalViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bannerModel: Banner) {
            val rightUrl = bannerModel.banner.replace("web:8001", "159.65.120.217")
            binding.ivProduct.load(rightUrl) {
                crossfade(true)
            }
        }

        companion object {
            fun from(parent: ViewGroup): HalalViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemBannerBinding.inflate(inflater, parent, false)
                return HalalViewHolder(binding)
            }
        }

    }


}