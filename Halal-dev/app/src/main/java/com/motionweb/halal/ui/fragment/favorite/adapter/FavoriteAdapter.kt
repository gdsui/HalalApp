package com.motionweb.halal.ui.fragment.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.motionweb.halal.data.network.catalog.models.CompanyDetail
import com.motionweb.halal.databinding.ItemFavoriteBinding
import com.motionweb.halal.ui.fragment.catalog.adapter.ItemClickListener

class FavoriteAdapter(private val listener: ItemClickListener) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteVH>() {

    private var companies: List<CompanyDetail?> = listOf()

    fun submitCompanies(companies: List<CompanyDetail?>?) {
        if (companies != null && companies.isNotEmpty()) {
            this.companies = companies
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVH =
        FavoriteVH.from(parent, listener)

    override fun onBindViewHolder(holder: FavoriteVH, position: Int) {
        companies[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = companies.size

    class FavoriteVH(private val vb: ItemFavoriteBinding, private val listener: ItemClickListener) :
        RecyclerView.ViewHolder(vb.root) {

        fun bind(company: CompanyDetail) {
            vb.ivLogo.load("http://159.65.120.217/" + company.logo)
            vb.tvName.text = company.name
            vb.cbFavourite.setOnCheckedChangeListener { _, b ->
                listener.onFavouriteClick(
                    company.id.toLong(),
                    b
                )
            }
        }

        companion object {
            fun from(parent: ViewGroup, listener: ItemClickListener): FavoriteVH {
                val inflater = LayoutInflater.from(parent.context)
                val vb = ItemFavoriteBinding.inflate(inflater, parent, false)
                return FavoriteVH(vb, listener)
            }
        }
    }

}