package com.motionweb.halal.ui.fragment.prayertime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.motionweb.halal.data.network.prayertime.PrayerTime
import com.motionweb.halal.databinding.ItemPrayerTimeBinding

class PrayerTimeAdapter : RecyclerView.Adapter<PrayerTimeAdapter.PrayerTimeVH>() {

    private var times: List<PrayerTime> = emptyList()

    fun submitItems(items: List<PrayerTime>) {
        times = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerTimeVH =
        PrayerTimeVH.from(parent)

    override fun onBindViewHolder(holder: PrayerTimeVH, position: Int) {
        holder.bind(times[position])
    }

    override fun getItemCount(): Int = times.size

    class PrayerTimeVH(private val vb: ItemPrayerTimeBinding) : RecyclerView.ViewHolder(vb.root) {

        fun bind(prayerTime: PrayerTime) {

        }

        companion object {
            fun from(parent: ViewGroup): PrayerTimeVH {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemPrayerTimeBinding.inflate(inflater, parent, false)
                return PrayerTimeVH(binding)
            }
        }
    }

}